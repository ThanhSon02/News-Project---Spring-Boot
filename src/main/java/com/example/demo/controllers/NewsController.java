package com.example.demo.controllers;

import java.util.List;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.CategoryModel;
import com.example.demo.model.NewsModel;
import com.example.demo.service.CategoryService;

@Controller
@RequestMapping("/news")
public class NewsController {
	@Autowired
	NewsService newsService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword, @RequestParam(name= "pageNum", defaultValue = "1") int pageNum, Model model) {
		Page<NewsModel> page = newsService.searchNews(keyword, pageNum, 8);
		model.addAttribute("newsList", page);
		List<CategoryModel> categoryModels = categoryService.getAllCategory();
		model.addAttribute("categories", categoryModels);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("keyword", keyword);
		
		return "category";
	}
}
