package sicredi.votacao.service.implementations;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.SessaoCadastroDTO;
import sicredi.votacao.dto.SessaoDTO;
import sicredi.votacao.entity.PautaEntity;
import sicredi.votacao.entity.SessaoEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.SessaoErroEnum;
import sicredi.votacao.repository.SessoesRepository;
import sicredi.votacao.service.interfaces.SessoesService;

@Service
@RequiredArgsConstructor
public class SessoesServiceImpl implements SessoesService {

    private final SessoesRepository sessoesRepository;
    private final PautaServiceImpl pautaServiceImpl;
    private final ObjectMapper objectMapper;

    @Value("${sessao.duracao}")
    private Long SESSAO_DURACAO;

    @Override
    public void create(SessaoCadastroDTO sessaoDTO) {
        PautaEntity pautaEntity = pautaServiceImpl.findById(sessaoDTO.getPautaId());

        sessoesRepository.saveAndFlush(SessaoEntity.builder()
                .duracao(Objects.isNull(sessaoDTO.getDuracao()) ? SESSAO_DURACAO : sessaoDTO.getDuracao())
                .pauta(pautaEntity).build());
    }

    @Override
    public SessaoEntity findById(Long id) {
        return sessoesRepository.findById(id).orElseThrow(
                () -> new ValidationsGlobalExceptions(SessaoErroEnum.SESSAO_NAO_ENCONTRADO.getDescricao()));
    }

    @Override
    public List<SessaoDTO> list() {
        return sessoesRepository.findAll()
                .stream()
                .map(sessao -> objectMapper.convertValue(sessao, SessaoDTO.class)).collect(Collectors.toList());
    }

}
