package sicredi.votacao.service.interfaces;

import java.util.List;

import sicredi.votacao.dto.AssociadoDTO;
import sicredi.votacao.entity.AssociadoEntity;

public interface AssociadosService {
    void create(AssociadoDTO associateDTO);

    List<AssociadoDTO> list(Long id, String cpf, String nome);

    AssociadoEntity findByCpf(String cpf);
}
