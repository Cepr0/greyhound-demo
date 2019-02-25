package greyhound;

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

@EnableAsync
@SpringBootApplication
public class Application {

	private final GreyhoundService greyhoundService;

	public Application(final GreyhoundService greyhoundService) {
		this.greyhoundService = greyhoundService;
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
		greyhoundService.process();
	}
}
