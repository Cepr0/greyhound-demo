package greyhound;

import greyhound.client.GreyHoundClient;
import greyhound.model.DriverAssignment;
import greyhound.service.GreyhoundService;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
public class Application {

	private final GreyHoundClient client;
	private final GreyhoundService service;

	public Application(final GreyHoundClient client, final GreyhoundService service) {
		this.client = client;
		this.service = service;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Open embedded H2 DB in the IDE:
	//
	// url: 'jdbc:h2:tcp://localhost:9092/mem:testdb'
	// username = 'sa'
	// password = ''
	//
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	@Async
	@EventListener
	public void onReady(ApplicationReadyEvent e) {
		List<DriverAssignment> assignments = client.getAssignments();

		CompletableFuture.allOf(
				service.processLocations(assignments),
				service.processCarriers(assignments)
		).join();

		service.processDrivers(assignments);
	}
}
