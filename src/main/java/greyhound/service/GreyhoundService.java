package greyhound.service;

import greyhound.entity.Carrier;
import greyhound.entity.Driver;
import greyhound.entity.Location;
import greyhound.model.DriverAssignment;
import greyhound.repository.CarrierRep;
import greyhound.repository.DriverRepo;
import greyhound.repository.LocationRepo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class GreyhoundService {

	private final DriverRepo driverRepo;
	private final LocationRepo locationRepo;
	private final CarrierRep carrierRepo;

	public GreyhoundService(
			final DriverRepo driverRepo,
			final LocationRepo locationRepo,
			final CarrierRep carrierRepo
	) {
		this.driverRepo = driverRepo;
		this.locationRepo = locationRepo;
		this.carrierRepo = carrierRepo;
	}

	@Async
	public CompletableFuture<List<Location>> processLocations(List<DriverAssignment> assignments) {
		return completedFuture(locationRepo.saveAll(assignments.stream()
				.map(a -> new Location(a.getLocationId(), a.getLocationName()))
				.distinct()
				.collect(Collectors.toList())
		));
	}

	@Async
	public CompletableFuture<List<Carrier>> processCarriers(List<DriverAssignment> assignments) {
		return completedFuture(carrierRepo.saveAll(assignments.stream()
				.map(a -> new Carrier(a.getCarrierId().trim()))
				.distinct()
				.collect(Collectors.toList())
		));
	}

	@Transactional
	public void processDrivers(List<DriverAssignment> assignments) {
		driverRepo.saveAll(assignments.stream()
				.map(a -> Driver.builder()
						.id(a.getDriverId())
						.firstName(a.getFirstName())
						.lastName(a.getLastName())
						.middleName(a.getMiddleName())
						.location(locationRepo.getOne(new Location.PK(a.getLocationId(), a.getLocationName())))
						.carrier(carrierRepo.getOne(a.getCarrierId().trim()))
						.build()
				)
//				.limit(20)
				.collect(Collectors.toList()));
	}
}
