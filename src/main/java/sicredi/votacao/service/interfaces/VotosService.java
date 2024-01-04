package sicredi.votacao.service.interfaces;

import java.util.List;

import sicredi.votacao.dto.VotoCadastroDTO;
import sicredi.votacao.dto.VotoDTO;

public interface VotosService {
    void create(VotoCadastroDTO votoCadastro);

    List<VotoDTO> list();
}
