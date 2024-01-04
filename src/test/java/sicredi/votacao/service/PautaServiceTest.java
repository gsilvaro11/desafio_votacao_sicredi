package sicredi.votacao.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import sicredi.votacao.dto.PautaCadastroDTO;
import sicredi.votacao.dto.PautaContabilizacaoDTO;
import sicredi.votacao.dto.PautaDTO;
import sicredi.votacao.entity.PautaEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.PautaErroEnum;
import sicredi.votacao.repository.PautaRepository;
import sicredi.votacao.service.implementations.PautaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaServiceImpl pautaServiceImpl;

    @Mock
    private PautaRepository pautaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private PautaEntity pautaEntity = new PautaEntity();
    private PautaEntity pautaAtualizada = new PautaEntity();

    @BeforeEach
    void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ReflectionTestUtils.setField(pautaServiceImpl, "objectMapper", objectMapper);

        pautaEntity.setId(1L);
        pautaEntity.setTitulo("Pauta de teste");
        pautaEntity.setDescricao("Descrição da pauta de teste");
        pautaEntity.setResultado("SIM");

        pautaAtualizada = pautaEntity;
    }

    @Test
    void create() {
        PautaCadastroDTO pautaDTO = new PautaCadastroDTO();
        pautaDTO.setTitulo("Pauta de teste");
        pautaDTO.setDescricao("Descrição da pauta de teste");

        when(pautaRepository.saveAndFlush(any(PautaEntity.class))).thenReturn(pautaEntity);
        pautaServiceImpl.create(pautaDTO);

        verify(pautaRepository, times(1)).saveAndFlush(any(PautaEntity.class));
    }

    @Test
    void testFindById() {
        Optional<PautaEntity> optionalPauta = Optional.of(pautaEntity);
        when(pautaRepository.findById(pautaEntity.getId())).thenReturn(optionalPauta);

        PautaEntity pauta = pautaServiceImpl.findById(pautaEntity.getId());

        assertAll(
                () -> assertNotNull(pauta),
                () -> assertEquals(pautaEntity.getId(), pauta.getId()),
                () -> assertEquals(pautaEntity.getTitulo(), pauta.getTitulo()),
                () -> assertEquals(pautaEntity.getDescricao(), pauta.getDescricao()),
                () -> assertEquals(pautaEntity.getResultado(), pauta.getResultado()));

        verify(pautaRepository, times(1)).findById(pautaEntity.getId());
    }

    @Test
    void testFindByIdShouldThrowValidationsGlobalExceptions() {
        when(pautaRepository.findById(pautaEntity.getId())).thenReturn(Optional.empty());

        assertThrows(ValidationsGlobalExceptions.class, () -> pautaServiceImpl.findById(pautaEntity.getId()),
                PautaErroEnum.PAUTA_NAO_ENCONTRADO.getDescricao());

        verify(pautaRepository, times(1)).findById(pautaEntity.getId());
    }

    @Test
    public void list() {
        List<PautaEntity> pautas = new ArrayList<>();
        pautas.add(pautaEntity);

        when(pautaRepository.list(pautaEntity.getId(), pautaEntity.getTitulo(), pautaEntity.getDescricao()))
                .thenReturn(pautas);

        List<PautaDTO> pautasDTO = pautaServiceImpl.list(pautaEntity.getId(), pautaEntity.getTitulo(),
                pautaEntity.getDescricao());

        assertAll(
                () -> assertEquals(pautasDTO.size(), pautas.size()),
                () -> assertNotNull(pautasDTO),
                () -> assertIterableEquals(pautasDTO, pautas.stream()
                        .map(p -> objectMapper.convertValue(p, PautaDTO.class)).collect(Collectors.toList())));

        verify(pautaRepository, times(1)).list(pautaEntity.getId(), pautaEntity.getTitulo(),
                pautaEntity.getDescricao());
    }

    @Test
    public void testAccounting() {
        Optional<PautaEntity> pautaOptional = Optional.of(pautaEntity);

        when(pautaRepository.findById(pautaEntity.getId())).thenReturn(pautaOptional);
        when(pautaRepository.countByIdAndVote(pautaEntity.getId(), true)).thenReturn(10L);
        when(pautaRepository.countByIdAndVote(pautaEntity.getId(), false)).thenReturn(5L);
        when(pautaRepository.save(pautaEntity)).thenReturn(pautaAtualizada);

        PautaContabilizacaoDTO pautaContabilizacaoDTO = pautaServiceImpl.accounting(pautaEntity.getId());

        assertAll(
                () -> assertEquals(pautaAtualizada.getId(), pautaContabilizacaoDTO.getId()),
                () -> assertEquals(pautaAtualizada.getTitulo(), pautaContabilizacaoDTO.getTitulo()),
                () -> assertEquals(pautaAtualizada.getDescricao(), pautaContabilizacaoDTO.getDescricao()),
                () -> assertEquals(pautaAtualizada.getResultado(), pautaContabilizacaoDTO.getResultado()),
                () -> assertEquals(10L, pautaContabilizacaoDTO.getPros()),
                () -> assertEquals(5L, pautaContabilizacaoDTO.getContras()));

        verify(pautaRepository, times(1)).findById(pautaEntity.getId());
        verify(pautaRepository, times(1)).countByIdAndVote(pautaEntity.getId(), true);
        verify(pautaRepository, times(1)).countByIdAndVote(pautaEntity.getId(), false);
        verify(pautaRepository, times(1)).save(pautaEntity);
    }

}