package sicredi.votacao.service.implementations;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.VotoCadastro;
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

        votosRepository.saveAndFlush(voto);        
    }

}