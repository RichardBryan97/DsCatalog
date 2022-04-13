package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Transactional
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();

         return list.stream().map( X -> new CategoryDTO(X)).collect(Collectors.toList());

    }

    @Transactional
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);

        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));

        return new CategoryDTO(entity);
    }
}
