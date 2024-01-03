package sicredi.votacao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "votos", schema = "desafio_sicredi")
public class VotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean voto;

    @Column(insertable = false, updatable = false)
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_associado", referencedColumnName = "id")
    private AssociadoEntity associado;

    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private SessaoEntity sessao;

}
