package sicredi.votacao.dto;

import lombok.Data;
import sicredi.votacao.entity.PautaEntity;

@Data
public class SessaoDTO {
    private Long id;
    private Long duracao;
    private PautaEntity pauta;
}
