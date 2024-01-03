package sicredi.votacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.SessaoEntity;

@Repository
public interface SessoesRepository extends JpaRepository<SessaoEntity, Long> {

    @Query(value = "FROM SessaoEntity se where se.pauta.id = :id")
    Optional<SessaoEntity> findByIdPauta(Long id);
    
}
