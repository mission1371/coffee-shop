package ee.coffeeshop.rest.product;

import ee.coffeeshop.domain.model.Product;
import org.springframework.stereotype.Component;

import static ee.coffeeshop.rest.product.ProductRestController.buildImageDownloadUri;

@Component
class ProductResourceConverter {

	public ProductResource convert(final Product product) {
		return ProductResource.builder().id(product.getId()).name(product.getName()).type(product.getType())
				.price(product.getPrice()).currencyCode(product.getCurrencyCode()).stock(product.getStock())
				.imageUrl(buildImageDownloadUri(product.getId())).build();
	}
}
