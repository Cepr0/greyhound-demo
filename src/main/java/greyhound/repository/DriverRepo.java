package greyhound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import greyhound.entity.Driver;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
}
