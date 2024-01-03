package sicredi.votacao.service.interfaces;

import java.util.List;

import sicredi.votacao.dto.PautaDTO;
import sicredi.votacao.entity.PautaEntity;

public interface PautaService {
    void create(PautaDTO decisaoDTO);

    PautaEntity findById(Long id);

    List<PautaDTO> list(Long id, String titulo, String descricao);

}
