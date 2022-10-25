package ee.coffeeshop.persistence.audit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Auditable {

	@NotNull
	@PastOrPresent
	@CreatedDate
	@Column("CREATED_AT")
	private Instant createdAt;

	@NotNull
	@CreatedBy
	@Column("CREATED_BY")
	private String createdBy;

	@NotNull
	@PastOrPresent
	@LastModifiedDate
	@Column("MODIFIED_AT")
	private Instant modifiedAt;

	@NotNull
	@LastModifiedBy
	@Column("MODIFIED_BY")
	private String modifiedBy;
}
