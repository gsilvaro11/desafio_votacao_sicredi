package sicredi.votacao.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VotoCadastro {
    @NotNull(message = "Campo não pode ser nulo.")
    private Long sessaoId;

    @NotBlank(message = "Campo não pode estar vazio.")
    @CPF(message = "O CPF é inválido.")
    private String cpf;
}
