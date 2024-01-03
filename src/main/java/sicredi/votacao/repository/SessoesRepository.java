package sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.SessaoEntity;

@Repository
public interface SessoesRepository extends JpaRepository<SessaoEntity, Long> {
    
}
