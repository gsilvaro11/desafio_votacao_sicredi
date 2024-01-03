package sicredi.votacao.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.VotoCadastro;
import sicredi.votacao.dto.VotoDTO;
import sicredi.votacao.entity.VotoEntity;
import sicredi.votacao.repository.VotosRepository;
import sicredi.votacao.service.interfaces.VotosService;

@Service
@RequiredArgsConstructor
public class VotosServiceImpl implements VotosService {

    private final VotosRepository votosRepository;
    private final SessoesServiceImpl sessoesServiceImpl;
    private final AssociadosServiceImpl associadosServiceImpl;
    private final ObjectMapper objectMapper;

    @Override
    public void create(VotoCadastro novoVoto) {
        VotoEntity voto = objectMapper.convertValue(novoVoto, VotoEntity.class);
        voto.setAssociado(associadosServiceImpl.findByCpf(novoVoto.getCpf()));
        voto.setSessao(sessoesServiceImpl.findById(novoVoto.getSessaoId()));
        // validar se o cpf já votou
        // validar se a votação n acabou e se a sessão está ativa

        votosRepository.saveAndFlush(voto);
    }

    public List<VotoDTO> list() {
        return votosRepository.findAll()
                .stream()
                .map(voto -> VotoDTO.builder()
                        .id(voto.getId())
                        .voto(voto.getVoto())
                        .dataCriacao(voto.getDataCriacao())
                        .cpfAssociado(voto.getAssociado().getCpf())
                        .build())
                .collect(Collectors.toList());
    }

}