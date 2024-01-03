package sicredi.votacao.service.interfaces;

import sicredi.votacao.dto.VotoCadastroDTO;

public interface VotosService {
    void create(VotoCadastroDTO votoCadastro);
}
