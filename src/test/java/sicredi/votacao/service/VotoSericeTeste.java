package sicredi.votacao.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import sicredi.votacao.dto.VotoCadastroDTO;
import sicredi.votacao.dto.VotoDTO;
import sicredi.votacao.entity.AssociadoEntity;
import sicredi.votacao.entity.SessaoEntity;
import sicredi.votacao.entity.VotoEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.repository.VotosRepository;
import sicredi.votacao.service.implementations.AssociadosServiceImpl;
import sicredi.votacao.service.implementations.SessoesServiceImpl;
import sicredi.votacao.service.implementations.VotosServiceImpl;
import sicredi.votacao.service.interfaces.ProducerService;
import sicredi.votacao.utils.CpfUtils;

@ExtendWith(MockitoExtension.class)
public class VotoSericeTeste {

        @InjectMocks
        private VotosServiceImpl votosServiceImpl;

        @Mock
        private SessoesServiceImpl sessoesServiceImpl;

        @Mock
        private AssociadosServiceImpl associadosServiceImpl;

        @Mock
        private ProducerService producerService;

        @Mock
        private VotosRepository votosRepository;

        private final ObjectMapper objectMapper = new ObjectMapper();
        private final VotoEntity votoEntity = new VotoEntity();
        private final AssociadoEntity associadoEntity = new AssociadoEntity();
        private final SessaoEntity sessaoEntity = new SessaoEntity();
        private final String cpfString = CpfUtils.generateCPF();

        @BeforeEach
        void init() {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ReflectionTestUtils.setField(votosServiceImpl, "objectMapper", objectMapper);

                sessaoEntity.setId(1L);
                sessaoEntity.setDuracao(60L);
                sessaoEntity.setDataCriacao(new Timestamp(0));

                associadoEntity.setId(1L);
                associadoEntity.setNome("Teste da Silva");
                associadoEntity.setCpf(cpfString);
                associadoEntity.setDataCriacao(new Timestamp(0));

                votoEntity.setId(1L);
                votoEntity.setSessao(sessaoEntity);
                votoEntity.setAssociado(associadoEntity);
                votoEntity.setDataCriacao(new Timestamp(0));
                votoEntity.setVoto(true);

        }

        @Test()
        public void successfulCreation() {
                String cpf = CpfUtils.generateCPF();
                VotoCadastroDTO novoVoto = VotoCadastroDTO.builder()
                                .cpf(cpf)
                                .voto(true)
                                .sessaoId(1L)
                                .build();

                when(associadosServiceImpl.findByCpf(novoVoto.getCpf())).thenReturn(associadoEntity);

                sessaoEntity.setDataCriacao(new Timestamp(System.currentTimeMillis() - sessaoEntity.getDuracao()));
                when(sessoesServiceImpl.findById(novoVoto.getSessaoId())).thenReturn(sessaoEntity);

                when(votosRepository.findBySessaoAndCpf(sessaoEntity.getId(), associadoEntity.getCpf()))
                                .thenReturn(Optional.empty());

                votosServiceImpl.create(novoVoto);

                verify(votosRepository, times(1)).saveAndFlush(any(VotoEntity.class));
                verify(producerService, times(1)).send(null, null);
        }

        @Test
        public void createShouldThrowValidationsGlobalExceptions() {
                String cpf = CpfUtils.generateCPF();
                VotoCadastroDTO novoVoto = VotoCadastroDTO.builder()
                                .cpf(cpf)
                                .voto(true)
                                .sessaoId(1L)
                                .build();

                when(associadosServiceImpl.findByCpf(novoVoto.getCpf())).thenReturn(associadoEntity);
                when(sessoesServiceImpl.findById(novoVoto.getSessaoId())).thenReturn(sessaoEntity);

                when(votosRepository.findBySessaoAndCpf(sessaoEntity.getId(), associadoEntity.getCpf()))
                                .thenReturn(Optional.empty());

                assertThrows(ValidationsGlobalExceptions.class, () -> votosServiceImpl.create(novoVoto));
                verify(votosRepository, never()).saveAndFlush(any(VotoEntity.class));
        }

        @Test
        public void list() {
                List<VotoEntity> votoEntities = new ArrayList<>();
                votoEntities.add(votoEntity);

                List<VotoDTO> votosDTO = votoEntities.stream()
                                .map(voto -> VotoDTO.builder()
                                                .id(voto.getId())
                                                .voto(voto.getVoto() ? "SIM" : "NÃO")
                                                .dataCriacao(voto.getDataCriacao())
                                                .cpfAssociado(voto.getAssociado().getCpf())
                                                .build())
                                .collect(Collectors.toList());

                assertAll(
                                () -> assertEquals(1L, votosDTO.get(0).getId()),
                                () -> assertEquals("SIM", votosDTO.get(0).getVoto()),
                                () -> assertEquals(cpfString, votosDTO.get(0).getCpfAssociado()));

        }
}
