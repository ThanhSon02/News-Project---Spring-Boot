package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.CategoryModel;
import com.example.demo.model.NewsModel;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/")
	public String HomePage(Model model) {
		List<CategoryModel> categoryModels = categoryService.getAllCategory();
		List<NewsModel> newsModels = newsService.findAllNews();
		model.addAttribute("categories", categoryModels);
		model.addAttribute("newsList", newsModels);
		return "index";
	}
	
	@GetMapping("/detail/{newsId}")
	public String DetailPage(@PathVariable int newsId ,Model model) {
		
		NewsModel newsModel = newsService.getDetail(newsId);
		List<CategoryModel> categoryModels = categoryService.getAllCategory();		
		
		model.addAttribute("newsDetail", newsModel);
		model.addAttribute("categories", categoryModels);
		
		return "detail";
	}

	@GetMapping("/category")
	public String CategoryPage(@RequestParam(name = "categoryId") int categoryId,@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,Model model) {
		List<CategoryModel> categoryModels = categoryService.getAllCategory();
		model.addAttribute("categories", categoryModels);
		CategoryModel categoryModel = categoryService.findById(categoryId);
		model.addAttribute("categoryData", categoryModel);
		Page<NewsModel> page = newsService.findByCategory(categoryModel, pageNum, 8);
		model.addAttribute("newsList", page);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		return "category";
	}
	
	@GetMapping("/most_view")
	public String MostViewPage(@RequestParam(name= "pageNum", defaultValue = "1") int pageNum, Model model) {
		Page<NewsModel> page = newsService.listViewDesc(pageNum, 8);
		model.addAttribute("newsList", page);
		model.addAttribute("mostView", true);
		List<CategoryModel> categoryModels = categoryService.getAllCategory();
		model.addAttribute("categories", categoryModels);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		
		return "category";
	}
}
