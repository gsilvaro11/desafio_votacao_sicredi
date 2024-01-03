package sicredi.votacao.service.interfaces;

import java.util.List;

import sicredi.votacao.dto.PautaContabilizacao;
import sicredi.votacao.dto.PautaDTO;
import sicredi.votacao.dto.PautaCadastroDTO;
import sicredi.votacao.entity.PautaEntity;

public interface PautaService {
    void create(PautaCadastroDTO decisaoDTO);

    PautaEntity findById(Long id);

    PautaContabilizacao accounting(Long id);

    List<PautaDTO> list(Long id, String titulo, String descricao);

}
