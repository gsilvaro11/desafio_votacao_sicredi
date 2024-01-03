package sicredi.votacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.AssociadoEntity;

@Repository
public interface AssociadosRepository extends JpaRepository<AssociadoEntity, Long> {

    @Query(value = "SELECT e FROM AssociadoEntity e " +
            "WHERE (e.id = :id or :id IS NULL) " +
            "AND (e.nome LIKE CONCAT('%', UPPER(:nome), '%') OR :nome IS NULL) " +
            "AND (e.cpf LIKE :cpf OR :cpf IS NULL) ")
    List<AssociadoEntity> list(Long id, String cpf, String nome);

    Optional<AssociadoEntity> findByCpf(String cpf);

}
