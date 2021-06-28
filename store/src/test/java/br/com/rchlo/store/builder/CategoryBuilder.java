package br.com.rchlo.store.builder;

import br.com.rchlo.store.domain.Category;

public class CategoryBuilder {

    private String name;
    private String slug;
    private int position;

    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public CategoryBuilder withPosition(int position) {
        this.position = position;
        return this;
    }

    public Category build() {
        return new Category(name, slug, position);
    }

}
