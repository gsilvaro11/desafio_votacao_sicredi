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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "sessao", schema = "desafio_sicredi")
@AllArgsConstructor
@NoArgsConstructor
public class SessaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long duracao;
    
    @Column(insertable = false, updatable = false)
    private Timestamp dataCriacao;

    @ManyToOne()
    @JoinColumn(name = "id_pauta")
    private PautaEntity pauta;

}
