package sicredi.votacao.service.interfaces;

import java.util.List;

import sicredi.votacao.dto.SessaoCadastroDTO;
import sicredi.votacao.dto.SessaoDTO;
import sicredi.votacao.entity.SessaoEntity;

public interface SessoesService {
    void create(SessaoCadastroDTO sessaoDTO);

    SessaoEntity findById(Long id);

    List<SessaoDTO> list();
}
