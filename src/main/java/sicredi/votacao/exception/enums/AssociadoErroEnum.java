package sicredi.votacao.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssociadoErroEnum {
    
    ASSOCIADO_CADASTRADO("Este associado já está cadastrado."),
    ASSOCIADO_NAO_ENCONTRADO("Associado não encontrado.");    

    private String descricao;
}
