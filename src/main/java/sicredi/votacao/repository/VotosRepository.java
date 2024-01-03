package sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sicredi.votacao.entity.VotoEntity;

@Repository
public interface VotosRepository extends JpaRepository<VotoEntity, Long> {

}