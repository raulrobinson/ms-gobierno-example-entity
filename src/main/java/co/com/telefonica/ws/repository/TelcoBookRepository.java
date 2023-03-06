package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.entity.TelcoBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelcoBookRepository extends JpaRepository<TelcoBookEntity, Long> {
  TelcoBookEntity findByCodebook(String codebook);
}
