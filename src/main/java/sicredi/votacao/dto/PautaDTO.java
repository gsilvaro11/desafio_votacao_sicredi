package sicredi.votacao.dto;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String resultado;

    @Column(insertable = false, updatable = false)
    private Timestamp dataCriacao;
}
