package sicredi.votacao.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PautaErroEnum {
    PAUTA_NAO_ENCONTRADO("Pauta não encontrada."),
    PAUTA_EM_ANDAMENTO("Pauta em andamento.");

    private String descricao;
}
