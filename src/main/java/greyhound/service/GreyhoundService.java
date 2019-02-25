package greyhound.service;

import greyhound.client.GreyHoundClient;
import greyhound.entity.Carrier;
import greyhound.entity.Driver;
import greyhound.entity.Location;
import greyhound.repository.CarrierRep;
import greyhound.repository.DriverRepo;
import greyhound.repository.LocationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GreyhoundService {

	private final GreyHoundClient client;
	private final DriverRepo driverRepo;
	private final LocationRepo locationRepo;
	private final CarrierRep carrierRepo;

	public GreyhoundService(
			final GreyHoundClient client,
			final DriverRepo driverRepo,
			final LocationRepo locationRepo,
			final CarrierRep carrierRepo
	) {
		this.client = client;
		this.driverRepo = driverRepo;
		this.locationRepo = locationRepo;
		this.carrierRepo = carrierRepo;
	}

	@Transactional
	public void process() {
		client.getAssignments()
				.stream()
				.limit(100)
				.forEach(a -> {
					log.debug("[d] Assignment: {}", a);

					Driver driver = new Driver();

					driver.setId(a.getDriverId());
					driver.setFirstName(a.getFirstName());
					driver.setLastName(a.getLastName());
					driver.setMiddleName(a.getMiddleName());

					driver.setLocation(
							locationRepo.findById(new Location.PK(a.getLocationId(), a.getLocationName()))
									.orElse(new Location(a.getLocationId(), a.getLocationName()))
					);

					driver.setCarrier(
							carrierRepo.findById(a.getCarrierId().trim())
									.orElse(new Carrier(a.getCarrierId().trim()))
					);

					driverRepo.saveAndFlush(driver);

					log.debug("[d] Driver: {}", driver);
				});
	}
}
