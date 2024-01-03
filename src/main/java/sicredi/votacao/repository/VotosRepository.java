package sicredi.votacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.VotoEntity;

@Repository
public interface VotosRepository extends JpaRepository<VotoEntity, Long> {

    @Query(value = "FROM VotoEntity v WHERE v.sessao.id = :id AND v.associado.cpf = :cpf")
    Optional<VotoEntity> findBySessaoAndCpf(Long id, String cpf);
}