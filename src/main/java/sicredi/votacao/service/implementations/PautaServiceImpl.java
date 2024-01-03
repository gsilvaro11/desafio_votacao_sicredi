package sicredi.votacao.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.PautaDTO;
import sicredi.votacao.entity.PautaEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.PautaErroEnum;
import sicredi.votacao.repository.PautaRepository;
import sicredi.votacao.service.interfaces.PautaService;

@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void create(PautaDTO pautaDTO) {
        pautaRepository.saveAndFlush(objectMapper.convertValue(pautaDTO, PautaEntity.class));
    }

    @Override
    public PautaEntity findById(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new ValidationsGlobalExceptions(PautaErroEnum.PAUTA_NAO_ENCONTRADO.getDescricao()));
    }

    @Override
    public List<PautaDTO> list(Long id, String titulo, String descricao) {
        return pautaRepository.list(id, titulo, descricao).stream()
                .map(pauta -> objectMapper.convertValue(pauta, PautaDTO.class)).collect(Collectors.toList());
    }

}
