package sicredi.votacao.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import sicredi.votacao.dto.SessaoCadastroDTO;
import sicredi.votacao.dto.SessaoDTO;
import sicredi.votacao.entity.PautaEntity;
import sicredi.votacao.entity.SessaoEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.SessaoErroEnum;
import sicredi.votacao.repository.SessoesRepository;
import sicredi.votacao.service.implementations.PautaServiceImpl;
import sicredi.votacao.service.implementations.SessoesServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SessoesServiceTest {

    @InjectMocks
    private SessoesServiceImpl sessoesServiceImpl;

    @Mock
    private SessoesRepository sessoesRepository;

    @Mock
    private PautaServiceImpl pautaServiceImpl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PautaEntity pautaEntity = new PautaEntity();
    private SessaoEntity sessaoEntity = new SessaoEntity();

    @BeforeEach
    void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ReflectionTestUtils.setField(sessoesServiceImpl, "objectMapper", objectMapper);

        pautaEntity.setId(1L);
        pautaEntity.setDescricao("Pauta de teste");

        sessaoEntity.setId(1L);
        sessaoEntity.setDuracao(60L);
        sessaoEntity.setPauta(pautaEntity);
    }

    @Test
    void create() {
        SessaoCadastroDTO sessaoDTO = new SessaoCadastroDTO();
        sessaoDTO.setPautaId(1L);
        sessaoDTO.setDuracao(60L);

        when(pautaServiceImpl.findById(sessaoDTO.getPautaId())).thenReturn(pautaEntity);
        when(sessoesRepository.saveAndFlush(any(SessaoEntity.class))).thenReturn(sessaoEntity);

        sessoesServiceImpl.create(sessaoDTO);

        verify(pautaServiceImpl, times(1)).findById(sessaoDTO.getPautaId());
        verify(sessoesRepository, times(1)).saveAndFlush(any(SessaoEntity.class));
    }

    @Test
    void finFindById() {
        Optional<SessaoEntity> optionalSessao = Optional.of(sessaoEntity);

        when(sessoesRepository.findById(sessaoEntity.getId())).thenReturn(optionalSessao);

        SessaoEntity sessao = sessoesServiceImpl.findById(sessaoEntity.getId());

        assertAll(
                () -> assertNotNull(sessao),
                () -> assertEquals(sessaoEntity.getId(), sessao.getId()),
                () -> assertEquals(sessaoEntity.getDuracao(), sessao.getDuracao()),
                () -> assertEquals(pautaEntity, sessao.getPauta()));

        verify(sessoesRepository, times(1)).findById(sessaoEntity.getId());
    }

    @Test
    void finFindByIdShouldThrowValidationsGlobalExceptions() {
        when(sessoesRepository.findById(sessaoEntity.getId())).thenReturn(Optional.empty());

        assertThrows(ValidationsGlobalExceptions.class, () -> sessoesServiceImpl.findById(sessaoEntity.getId()),
                SessaoErroEnum.SESSAO_NAO_ENCONTRADO.getDescricao());

        verify(sessoesRepository, times(1)).findById(sessaoEntity.getId());
    }

    @Test
    public void list() {
        List<SessaoEntity> sessoes = new ArrayList<>();
        sessoes.add(sessaoEntity);

        when(sessoesRepository.findAll()).thenReturn(sessoes);

        List<SessaoDTO> sessoesDTO = sessoesServiceImpl.list();

        assertAll(
                () -> assertEquals(1, sessoesDTO.size()),
                () -> assertNotNull(sessoesDTO),
                () -> assertIterableEquals(sessoesDTO, sessoes.stream()
                        .map(a -> objectMapper.convertValue(a, SessaoDTO.class)).collect(Collectors.toList())));

        verify(sessoesRepository, times(1)).findAll();
    }
}
