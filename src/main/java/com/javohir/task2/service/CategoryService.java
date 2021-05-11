package com.javohir.task2.service;

import com.javohir.task2.entity.Category;
import com.javohir.task2.entity.ProgrammingLanguage;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.CategoryDTO;
import com.javohir.task2.repository.CategoryRepository;
import com.javohir.task2.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    PLRepository plRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse add(CategoryDTO categoryDTO) {
        Integer languageId = categoryDTO.getProgrammingLanguageId();
        Optional<ProgrammingLanguage> optionalLanguage = plRepository.findById(languageId);
        if (!optionalLanguage.isPresent()) {
            return new ApiResponse("Language not found", false);
        }

        Category category = new Category();
        category.setDescription(categoryDTO.getDescription());
        category.setProgrammingLanguage(optionalLanguage.get());
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new ApiResponse("Successfully added", true);
    }

    public List<Category> getAll() {
        List<Category> all = categoryRepository.findAll();
        return all;
    }

    public ApiResponse getOneById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResponse("Cat is not found", false);
        return new ApiResponse("Success", true, optionalCategory.get() );
    }

    public ApiResponse delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse edit(Integer id, CategoryDTO categoryDTO) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new ApiResponse("Category not found", false);

        Optional<ProgrammingLanguage> optionalLanguage = plRepository.findById(categoryDTO.getProgrammingLanguageId());
        if (!optionalLanguage.isPresent()) return new ApiResponse("Language not found", false);

        Category category = optionalCategory.get();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setProgrammingLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return new ApiResponse("Successfully edited", true);
    }
}
