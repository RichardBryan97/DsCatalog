package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Transactional
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
        Page<Category> list = categoryRepository.findAll(pageRequest);

         return list.map( X -> new CategoryDTO(X));

    }

    @Transactional
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);

        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {

        try {
            Category entity = categoryRepository.getOne(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id Not Found" + id);
        }
    }

    public void delete(Long id) {

        try{
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id Not Found" + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity Violation");
        }

    }


}
