package br.com.rchlo.store.dto;

import br.com.rchlo.store.domain.Category;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryDto {

    @NotBlank @Size(max = 255)
    private final String name;

    @NotBlank @Size(max = 255) @Pattern(regexp = "[\\w-]+")
    private final String slug;

    @JsonCreator
    public CategoryDto(Category category) {
        this.name = category.getName();
        this.slug = category.getSlug();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Category entityAt(int oldLastPosition) {
        int lastPosition = oldLastPosition++;
        return new Category(this.name, this.slug, lastPosition);
    }

}
