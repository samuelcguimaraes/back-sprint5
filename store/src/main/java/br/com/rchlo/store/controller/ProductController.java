package br.com.rchlo.store.controller;

import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.dto.ProductDto;
import br.com.rchlo.store.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable("product-list")
    @GetMapping("/products")
    public List<ProductDto> productsByColorReport(@PageableDefault(sort = "name") Pageable pageable) {
        return productRepository.findAllWithImagesAndCategoryAndAvailableSizes(pageable).stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @GetMapping("/reports/products/by-color")
    public List<ProductByColorDto> productByColorReport() {
        return productRepository.productsByColor();
    }


    @CacheEvict(value = "product-list", allEntries = true)
    @GetMapping("/products/clear-cache")
    public String clearCache() {
        return "Product cache evicted!";
    }

}
