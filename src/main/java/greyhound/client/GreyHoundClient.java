package greyhound.client;

import greyhound.model.DriverAssignment;
import greyhound.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class GreyHoundClient {

	private static final String URL = "https://pegasus.greyhound.com/busTracker/dispatch/driverBusAssignment";

	private final RestTemplate restTemplate;

	public GreyHoundClient() {
		this.restTemplate = new RestTemplate();
	}

	public List<DriverAssignment> getAssignments() {
		Response response = restTemplate.getForObject(URL, Response.class);
		if (response == null) {
			throw new IllegalStateException("Couldn't get response from " + URL);
		}
		return response.getAssignments();
	}
}
