package sicredi.votacao.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.AssociadoDTO;
import sicredi.votacao.entity.AssociadoEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.AssociadoErroEnum;
import sicredi.votacao.repository.AssociadosRepository;
import sicredi.votacao.service.interfaces.AssociadosService;

@Service
@RequiredArgsConstructor
public class AssociadosServiceImpl implements AssociadosService {

    private final AssociadosRepository associadosRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void create(AssociadoDTO associateDTO) {
        if (associadosRepository.findByCpf(associateDTO.getCpf()).isPresent()) {
            throw new ValidationsGlobalExceptions(AssociadoErroEnum.ASSOCIADO_CADASTRADO.getDescricao());
        }

        associadosRepository.saveAndFlush(objectMapper.convertValue(associateDTO, AssociadoEntity.class));
    }

    @Override
    public AssociadoEntity findByCpf(String cpf) {
        return associadosRepository.findByCpf(cpf).orElseThrow(() -> new ValidationsGlobalExceptions(
                AssociadoErroEnum.ASSOCIADO_NAO_ENCONTRADO.getDescricao()));
    }

    @Override
    public List<AssociadoDTO> list(Long id, String cpf, String nome) {
        return associadosRepository.list(id, cpf, nome).stream()
                .map(associado -> objectMapper.convertValue(associado, AssociadoDTO.class))
                .collect(Collectors.toList());
    }

}
