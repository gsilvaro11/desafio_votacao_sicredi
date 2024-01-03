package sicredi.votacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PautaDTO {

    private Long id;

    @NotBlank(message = "Campo não pode estar vazio.")
    private String titulo;

    @NotBlank(message = "Campo não pode estar vazio.")
    private String descricao;

}
