package sicredi.votacao.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class VotoDTO {
    private Long id;
    private String voto;
    private Timestamp dataCriacao;
    private String cpfAssociado;
}
