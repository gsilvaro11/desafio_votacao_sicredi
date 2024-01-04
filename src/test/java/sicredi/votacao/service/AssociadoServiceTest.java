package sicredi.votacao.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import sicredi.votacao.dto.AssociadoCadastroDTO;
import sicredi.votacao.dto.AssociadoDTO;
import sicredi.votacao.entity.AssociadoEntity;
import sicredi.votacao.repository.AssociadosRepository;
import sicredi.votacao.service.implementations.AssociadosServiceImpl;
import sicredi.votacao.utils.CpfUtils;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    @InjectMocks
    private AssociadosServiceImpl associadosServiceImpl;

    @Mock
    private AssociadosRepository associadosRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private AssociadoCadastroDTO associadoCadastroDTO;
    private AssociadoEntity associadoEntity = new AssociadoEntity();

    @BeforeEach
    void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ReflectionTestUtils.setField(associadosServiceImpl, "objectMapper", objectMapper);

        associadoCadastroDTO = AssociadoCadastroDTO.builder()
                .nome("Teste da Silva da Silva")
                .cpf(CpfUtils.generateCPF())
                .build();

        associadoEntity.setId(1L);
        associadoEntity.setCpf(CpfUtils.generateCPF());
        associadoEntity.setNome("Teste da Silva da Silva");
        associadoEntity.setDataCriacao(new Timestamp(0));

    }

    @Test
    void create() {
        when(associadosRepository.saveAndFlush(any(AssociadoEntity.class))).thenReturn(associadoEntity);

        associadosServiceImpl.create(associadoCadastroDTO);

        verify(associadosRepository, times(1)).saveAndFlush(any(AssociadoEntity.class));
    }

    @Test
    void list() {
        List<AssociadoEntity> associados = new ArrayList<>();
        associados.add(associadoEntity);

        when(associadosRepository.list(null, null, null)).thenReturn(associados);

        List<AssociadoDTO> result = associadosServiceImpl.list(null, null, null);

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertNotNull(result),
                () -> assertIterableEquals(result, associados.stream()
                        .map(a -> objectMapper.convertValue(a, AssociadoDTO.class)).collect(Collectors.toList())));

        verify(associadosRepository, times(1)).list(null, null, null);
    }

    @Test
    void findByCpf() {
        String cpf = CpfUtils.generateCPF();

        AssociadoEntity associado = new AssociadoEntity();
        associado.setCpf(cpf);

        Optional<AssociadoEntity> optionalAssociado = Optional.of(associado);

        when(associadosRepository.findByCpf(cpf)).thenReturn(optionalAssociado);

        AssociadoEntity resultado = associadosServiceImpl.findByCpf(cpf);

        assertNotNull(resultado);
        assertEquals(cpf, resultado.getCpf());
    }

}
