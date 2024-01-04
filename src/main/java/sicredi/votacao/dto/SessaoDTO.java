package sicredi.votacao.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SessaoDTO {
    private Long id;
    private Long duracao;
    private Timestamp dataCriacao;
    private PautaDTO pauta;
}
