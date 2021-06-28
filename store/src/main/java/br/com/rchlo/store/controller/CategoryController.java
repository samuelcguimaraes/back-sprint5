package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.dto.CategoryDto;
import br.com.rchlo.store.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public List<CategoryDto> categories() {
        return categoryRepository.findAllByOrderByPositionAsc().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @GetMapping("/admin/categories/{id}")
    public CategoryDto detail(@PathVariable("id") Long id) {
        return categoryRepository.findById(id).map(CategoryDto::new).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Void> create(@RequestBody @Valid CategoryDto categoryDto, UriComponentsBuilder uriBuilder) {
        int oldLastPosition = categoryRepository.maxPosition();
        Category category = categoryRepository.save(categoryDto.entityAt(oldLastPosition));
        URI uri = uriBuilder.path("/admin/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
