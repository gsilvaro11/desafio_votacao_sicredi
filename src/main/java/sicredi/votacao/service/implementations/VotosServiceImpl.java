package sicredi.votacao.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.VotoCadastroDTO;
import sicredi.votacao.dto.VotoDTO;
import sicredi.votacao.entity.VotoEntity;
import sicredi.votacao.exception.ValidationsGlobalExceptions;
import sicredi.votacao.exception.enums.VotoErroEnum;
import sicredi.votacao.repository.VotosRepository;
import sicredi.votacao.service.interfaces.AssociadosService;
import sicredi.votacao.service.interfaces.SessoesService;
import sicredi.votacao.service.interfaces.VotosService;

@Service
@RequiredArgsConstructor
public class VotosServiceImpl implements VotosService {

    private final VotosRepository votosRepository;
    private final SessoesService sessoesService;
    private final AssociadosService associadosService;
    private final ObjectMapper objectMapper;

    @Override
    public void create(VotoCadastroDTO novoVoto) {
        VotoEntity voto = objectMapper.convertValue(novoVoto, VotoEntity.class);
        voto.setAssociado(associadosService.findByCpf(novoVoto.getCpf()));
        voto.setSessao(sessoesService.findById(novoVoto.getSessaoId()));

        if (votosRepository.findBySessaoAndCpf(voto.getSessao().getId(), voto.getAssociado().getCpf()).isPresent()) {
            throw new ValidationsGlobalExceptions(VotoErroEnum.ASSOCIADO_CADASTRADO_NA_SESSAO.getDescricao());
        }

        if (!voto.getSessao().validaSessaoAtiva()) {
            throw new ValidationsGlobalExceptions(VotoErroEnum.VOTACAO_ENCERRADA.getDescricao());
        }

        votosRepository.saveAndFlush(voto);
    }

    @Override
    public List<VotoDTO> list() {
        return votosRepository.findAll()
                .stream()
                .map(voto -> VotoDTO.builder()
                        .id(voto.getId())
                        .voto(voto.getVoto() ? "SIM" : "NÃO")
                        .dataCriacao(voto.getDataCriacao())
                        .cpfAssociado(voto.getAssociado().getCpf())
                        .idPauta(voto.getSessao().getPauta().getId())
                        .build())
                .collect(Collectors.toList());
    }

}