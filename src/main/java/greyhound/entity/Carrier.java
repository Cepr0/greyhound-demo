package greyhound.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "carriers")
public class Carrier implements Persistable<String> {
	@Id private String carrierId;

	public Carrier(final String carrierId) {
		this.carrierId = carrierId;
	}

	@Override
	public String getId() {
		return carrierId;
	}

	@Override
	public boolean isNew() {
		return true;
	}
}

//	create table CARRIERS (
//	  CARRIER_ID VARCHAR(255) not null primary key
//	);

