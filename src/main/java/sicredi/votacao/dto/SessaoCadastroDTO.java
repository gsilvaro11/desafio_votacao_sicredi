package sicredi.votacao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessaoCadastroDTO {

    @NotNull(message = "Campo não pode ser nulo.")
    private Long pautaId;

    private Long duracao;
}
