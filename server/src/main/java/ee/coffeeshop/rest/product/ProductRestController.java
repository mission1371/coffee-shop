package ee.coffeeshop.rest.product;

import ee.coffeeshop.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Objects;

import static ee.coffeeshop.rest.product.ProductRestController.PRODUCTS_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCTS_PATH)
class ProductRestController {

	private final ProductService service;
	private final ProductResourceConverter converter;
	public static final String PRODUCTS_PATH = "/products";
	private static final String IMAGE_DOWNLOAD_URI_PARAM = "{id}";
	private static final String IMAGE_DOWNLOAD_URI = "/" + IMAGE_DOWNLOAD_URI_PARAM + "/image";

	@GetMapping
	public Flux<ProductResource> getByProductType(@RequestParam(required = false) final Long type) {
		if (Objects.nonNull(type)) {
			return service.getByType(type).map(converter::convert);
		} else {
			return service.findAll().map(converter::convert);
		}
	}

	@GetMapping(path = IMAGE_DOWNLOAD_URI)
	public Mono<Resource> getProductImage(@PathVariable(name = "id") final Long id) {
		// TODO cache headers for the image
		return service.getById(id).map(product -> new ClassPathResource(product.getImageUrl()));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<ProductResource> update(@Valid @RequestBody final ProductUpdateResource resource,
			@PathVariable final Long id) {
		if (resource.getStock() < 0) {
			return service.reduce(id, Math.abs(resource.getStock())).map(converter::convert);
		} else {
			return service.increase(id, resource.getStock()).map(converter::convert);
		}
	}

	public static String buildImageDownloadUri(final Long id) {
		return PRODUCTS_PATH.concat(IMAGE_DOWNLOAD_URI).replace(IMAGE_DOWNLOAD_URI_PARAM, String.valueOf(id));
	}

}