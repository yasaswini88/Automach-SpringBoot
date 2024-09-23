//package com.example.Automach.Service;
//
//public class CategoryService {
//}
package com.example.Automach.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Automach.entity.Category;
import com.example.Automach.repo.CategoryRepo;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
