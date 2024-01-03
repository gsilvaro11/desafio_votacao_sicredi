package sicredi.votacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaCadastroDTO {
    
    @NotBlank(message = "Campo não pode estar vazio.")
    private String titulo;

    @NotBlank(message = "Campo não pode estar vazio.")
    private String descricao;

}
