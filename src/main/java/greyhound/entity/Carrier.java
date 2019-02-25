package greyhound.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carriers")
public class Carrier {
	@Id private String carrierId;

	public Carrier(final String carrierId) {
		this.carrierId = carrierId;
	}
}

//	create table CARRIERS (
//	  CARRIER_ID VARCHAR(255) not null primary key
//	);

