package sicredi.votacao.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoCadastroDTO {
    @NotBlank(message = "Campo não pode estar vazio.")
    private String nome;

    @CPF(message = "O CPF é inválido.")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve ser apenas numérico.")
    private String cpf;
}
