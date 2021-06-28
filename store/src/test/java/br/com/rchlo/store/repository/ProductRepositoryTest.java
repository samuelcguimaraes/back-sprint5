package br.com.rchlo.store.repository;

import br.com.rchlo.store.builder.CategoryBuilder;
import br.com.rchlo.store.builder.ProductBuilder;
import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.domain.Color;
import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/schema.sql")
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void loadData() {
        Category infantil = aCategory();
        aProduct(infantil);
        anotherProduct(infantil);
        yetAnotherProduct(infantil);
    }

    @Test
    void shouldListAllProductsOrderedByName() {
        List<Product> products = productRepository.findAllWithImagesAndCategoryAndAvailableSizes(PageRequest.of(0, 10, Sort.by("name")));

        assertEquals(3, products.size());

        Product aProduct = products.get(0);
        assertEquals(2L, aProduct.getCode());
        assertEquals("Blusa de Moletom Infantil Mario Bros Vermelho", aProduct.getName());

        Product anotherProduct = products.get(1);
        assertEquals(7L, anotherProduct.getCode());
        assertEquals("Jaqueta Puffer Juvenil Com Capuz Super Mario Branco", anotherProduct.getName());

        Product yetAnotherProduct = products.get(2);
        assertEquals(1L, yetAnotherProduct.getCode());
        assertEquals("Regata Infantil Mario Bros Branco", yetAnotherProduct.getName());
    }

    @Test
    void shouldRetrieveProductsByColor() {
        List<ProductByColorDto> productsByColor = productRepository.productsByColor();
        productsByColor.sort(Comparator.comparing(ProductByColorDto::getColorDescription));

        assertEquals(2, productsByColor.size());

        ProductByColorDto aProductDto = productsByColor.get(0);
        assertEquals("Branca", aProductDto.getColorDescription());
        assertEquals(2, aProductDto.getAmount());

        ProductByColorDto anotherProductDto = productsByColor.get(1);
        assertEquals("Vermelha", anotherProductDto.getColorDescription());
        assertEquals(1, anotherProductDto.getAmount());
    }

    private Product yetAnotherProduct(Category category) {
        Product product = new ProductBuilder().withCode(2L).withName("Blusa de Moletom Infantil Mario Bros Vermelho")
                .withDescription("A Blusa de Moletom Infantil Mario Bros Vermelho é quentinha e divertida!")
                .withSlug("blusa-infantil-moletom-mario-bros-vermelho-14125129_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("79.90")).withDiscount(null).withColor(Color.RED).withWeightInGrams(126)
                .withCategory(category)
                .build();
        entityManager.persist(product);
        return product;
    }

    private Product anotherProduct(Category category) {
        Product product = new ProductBuilder().withCode(1L).withName("Regata Infantil Mario Bros Branco")
                .withDescription("A Regata Infantil Mario Bros Branco é confeccionada em fibra natural. Aposte!")
                .withSlug("regata-infantil-mario-bros-branco-14040174_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("29.90")).withDiscount(null).withColor(Color.WHITE).withWeightInGrams(98)
                .withCategory(category)
                .build();
        entityManager.persist(product);
        return product;
    }

    private Product aProduct(Category category) {
        Product product = new ProductBuilder().withCode(7L).withName("Jaqueta Puffer Juvenil Com Capuz Super Mario Branco")
                .withDescription("A Jaqueta Puffer Juvenil Com Capuz Super Mario Branco é confeccionada em material sintético.")
                .withSlug("jaqueta-puffer-juvenil-com-capuz-super-mario-branco-13834193_sku").withBrand("Nintendo")
                .withPrice(new BigDecimal("199.90")).withDiscount(null).withColor(Color.WHITE).withWeightInGrams(147)
                .withCategory(category)
                .build();
        entityManager.persist(product);
        return product;
    }

    private Category aCategory() {
        Category category = new CategoryBuilder().withName("Infantil").withSlug("infantil").withPosition(1).build();
        entityManager.persist(category);
        return category;
    }


}