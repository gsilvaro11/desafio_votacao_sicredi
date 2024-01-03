package sicredi.votacao.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessaoErroEnum {
    SESSAO_NAO_ENCONTRADO("Sessão não encontrada.");

    private String descricao;

}
