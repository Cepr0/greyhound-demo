package greyhound.repository;

import greyhound.entity.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRep extends JpaRepository<Carrier, String> {
}
