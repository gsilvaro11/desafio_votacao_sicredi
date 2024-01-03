package sicredi.votacao.dto;

import java.sql.Timestamp;

import lombok.Data;
import sicredi.votacao.entity.PautaEntity;

@Data
public class SessaoDTO {
    private Long id;
    private Long duracao;
    private Timestamp dataCriacao;
    private PautaEntity pauta;
}
