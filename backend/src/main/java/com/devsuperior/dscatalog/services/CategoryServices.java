package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Transactional
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
