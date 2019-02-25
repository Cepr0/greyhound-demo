package greyhound.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "drivers")
public class Driver implements Persistable<Long> {

	@Id
	private Long id;

	private String firstName;
	private String lastName;
	private String middleName;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "carrierId", foreignKey = @ForeignKey(name = "drivers_carriers"))
	private Carrier carrier;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns(
			value = {@JoinColumn(name = "locationId"), @JoinColumn(name = "locationName")},
			foreignKey = @ForeignKey(name = "drivers_locations")
	)
	private Location location;

	@Override
	public boolean isNew() {
		return true;
	}
}

//	create table DRIVERS (
//	  ID            bigint not null primary key,
//	  FIRST_NAME    varchar(255),
//	  LAST_NAME     varchar(255),
//	  MIDDLE_NAME   varchar(255),
//	  CARRIER_ID    varchar(255),
//	  LOCATION_ID   bigint,
//	  LOCATION_NAME varchar(255),
//	  constraint DRIVERS_CARRIERS foreign key (CARRIER_ID) references CARRIERS,
//	  constraint DRIVERS_LOCATIONS foreign key (LOCATION_ID, LOCATION_NAME) references LOCATIONS
// );