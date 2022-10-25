package ee.coffeeshop.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account {

	String id;
	boolean isAdmin;

}
