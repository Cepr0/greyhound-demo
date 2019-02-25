package greyhound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverAssignment {

	// location
	@JsonProperty("home_loc_6") private Long locationId;
	@JsonProperty("home_loc_3") private String locationName;

	// carrier
	@JsonProperty("carrier_cd") private String carrierId;

	// driver
	@JsonProperty("oper_nbr") private Long driverId;
	@JsonProperty("first_name") private String firstName;
	@JsonProperty("last_name") private String lastName;
	@JsonProperty("middle_init") private String middleName;

	// unused
	@JsonProperty("oper_class") private String operClass;
}
