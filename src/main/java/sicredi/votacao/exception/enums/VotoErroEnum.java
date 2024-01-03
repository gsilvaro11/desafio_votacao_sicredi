package sicredi.votacao.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VotoErroEnum {
    ASSOCIADO_CADASTRADO_NA_SESSAO("Associado já cadastrado na sessão.");

    private String descricao;
}
