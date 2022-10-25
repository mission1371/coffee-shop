package ee.coffeeshop.persistence.product;

import ee.coffeeshop.persistence.audit.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString(callSuper = true)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class ProductEntity extends Auditable {

	@Id
	@Column("ID")
	private Long id;

	@NotNull
	@Column("TYPE")
	private Long type;

	@NotEmpty
	@Length(min = 1, max = 1024)
	@Column("NAME")
	private String name;

	@NotEmpty
	@Min(0)
	@Column("STOCK")
	private int stock;

	@NotNull
	@DecimalMax(value = "999999999999999.99")
	@Column("PRICE")
	private BigDecimal price;

	@NotEmpty
	@Length(min = 3, max = 3)
	@Column("CURRENCY_CODE")
	private String currencyCode;

	@NotEmpty
	@Length(min = 1, max = 1024)
	@Column("IMAGE_URL")
	private String imageUrl;

}
