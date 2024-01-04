package sicredi.votacao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoCadastroDTO {

    @NotNull(message = "Campo não pode ser nulo.")
    private Long pautaId;

    private Long duracao;
}
