package br.com.rchlo.store.builder;

import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.domain.Color;
import br.com.rchlo.store.domain.Product;

import java.math.BigDecimal;

public class ProductBuilder {

    private Long code;
    private String name;
    private String description;
    private String slug;
    private String brand;
    private BigDecimal price;
    private BigDecimal discount;
    private Color color;
    private Integer weightInGrams;
    private Category category;

    public ProductBuilder withCode(Long code) {
        this.code = code;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public ProductBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public ProductBuilder withWeightInGrams(Integer weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public Product build() {
        return new Product(code, name, description, slug, brand, price, discount, color, weightInGrams, category);
    }
}