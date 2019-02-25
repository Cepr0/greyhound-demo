package greyhound.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Response {
	@JsonProperty("results") private List<DriverAssignment> assignments;
}
