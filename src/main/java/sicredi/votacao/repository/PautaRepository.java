package sicredi.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.PautaEntity;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {
    
    @Query(value = "SELECT p FROM PautaEntity p " +
    "WHERE (p.id = :id or :id IS NULL) " +
    "AND (p.titulo LIKE CONCAT('%', UPPER(:titulo), '%') OR :titulo IS NULL) " +
    "AND (p.descricao LIKE CONCAT('%', UPPER(:descricao), '%') OR :descricao IS NULL) ")
    List<PautaEntity> list(Long id, String titulo, String descricao);

}
