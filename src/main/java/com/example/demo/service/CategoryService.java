package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CategoryModel;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryModel findById(int categoryId) {
		return categoryRepository.findById(categoryId).get();
	}
	public List<CategoryModel> getAllCategory() {
		return categoryRepository.findAll();
	}
	
	public CategoryModel save(CategoryModel categoryModel) {
		return categoryRepository.save(categoryModel);
	}
}
