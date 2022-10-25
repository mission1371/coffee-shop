package ee.coffeeshop.persistence.product;

import ee.coffeeshop.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
class ProductEntityConverter {

	public Product convert(final ProductEntity entity) {
		return Product.builder().id(entity.getId()).type(entity.getType()).name(entity.getName())
				.price(entity.getPrice()).stock(entity.getStock()).currencyCode(entity.getCurrencyCode())
				.imageUrl(entity.getImageUrl()).build();
	}
}
