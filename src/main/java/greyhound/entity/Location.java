package greyhound.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "locations")
@IdClass(Location.PK.class )
public class Location implements Persistable<Location.PK> {

	@Id private Long locationId;
	@Id private String locationName;

	public PK getId() {
		return new PK(locationId, locationName);
	}

	public void setId(PK id) {
		this.locationId = id.getLocationId();
		this.locationName = id.getLocationName();
	}

	public Location(final Long locationId, final String locationName) {
		this.locationId = locationId;
		this.locationName = locationName;
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PK implements Serializable {
		private Long locationId;
		private String locationName;
	}
}

//	create table LOCATIONS (
//	  LOCATION_ID   bigint       not null,
//	  LOCATION_NAME varchar(255) not null,
//	  primary key (LOCATION_ID, LOCATION_NAME)
//	);
