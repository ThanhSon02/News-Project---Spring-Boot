package com.example.demo.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CategoryModel;
import com.example.demo.model.NewsModel;
import com.example.demo.service.CategoryService;
import com.example.demo.service.FileUpload;
import com.example.demo.service.FileUploadCloundImpl;
import com.example.demo.service.NewsService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	NewsService newsService;
	@Autowired
	FileUpload fileUploadService;
	@Autowired
	FileUploadCloundImpl fileUploadCloundImpl;
	
	@GetMapping("/dashboard")
	public String AdminPage(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum) {
		Page<NewsModel> list = newsService.newsPage(pageNum, 10);
		model.addAttribute("newsList", list);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		return "admin_dashboard";
	}

	@GetMapping("/category")
	public String AdminAddCategory(Model model) {
		model.addAttribute("category", new CategoryModel());
		return "admin_category";
	}
	
	@GetMapping("/formInput")
	public String AdminUpload(Model model) {
		List<CategoryModel> list = categoryService.getAllCategory();
		model.addAttribute("categories", list);
		model.addAttribute("news", new NewsModel());
		return "admin_add_form";
	}
	
	
	@PostMapping("/formInput")
	public String save(@ModelAttribute("news") NewsModel newsModel, RedirectAttributes redirectAttributes, Model model) {
		Boolean errorFlag = false;
		if(newsModel.getNewsTitle().length() == 0) {
			List<CategoryModel> list = categoryService.getAllCategory();
			errorFlag = true;
			model.addAttribute("errorNewsTitle", "Tiêu đề không được để trống");		
			model.addAttribute("news", newsModel);
			model.addAttribute("categories", list);
		} else if (newsModel.getNewsTitle().length() <= 5) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorNewsTitle", "Tiêu đề phải lớn hơn 5 ký tự");				
			model.addAttribute("news", newsModel);
		}
		
		if(newsModel.getContent().length() == 0) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorContent", "Nội dung không được để trống");
			model.addAttribute("news", newsModel);
		} else if (newsModel.getContent().length() <= 15) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorContent", "Nội dung phải lớn hơn 15 ký tự");						
			model.addAttribute("news", newsModel);
		}
		
		if(newsModel.getThumbnailFile().getOriginalFilename().length() == 0) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorFile", "Thumbnail không được để trống");										
			model.addAttribute("news", newsModel);
		} else if (!newsModel.getThumbnailFile().getContentType().startsWith("image/")) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorFile", "Chỉ chấp nhận file ảnh");											
			model.addAttribute("news", newsModel);
		}
		
		if(errorFlag) {
			return "admin_add_form";	
		}
		
		try {
			String fileName = fileUploadService.uploadImage(newsModel.getThumbnailFile());
			newsModel.setPhotoThumbnail(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		newsService.save(newsModel);
		redirectAttributes.addFlashAttribute("message", "Thêm tin thành công");
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/news/delete/{newsId}")
	public String delete(@PathVariable(name = "newsId") int newsId) {
		newsService.deleteNews(newsId);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/news/update/{newsId}")
	public String edit(@PathVariable int newsId, Model model) {
		NewsModel newsModelEdit = newsService.getNews(newsId);
		List<CategoryModel> categoryModels = categoryService.getAllCategory();
		model.addAttribute("categories", categoryModels);
		model.addAttribute("dataUpdate", newsModelEdit);
		return "admin_update_form";
	}
	
	@PostMapping("/news/update/{newsId}")
	public String update(@ModelAttribute("dataUpdate") NewsModel newsModel, @PathVariable int newsId,RedirectAttributes redirectAttributes, Model model) {
		Boolean errorFlag = false;
		if(newsModel.getNewsTitle().length() == 0) {
			List<CategoryModel> list = categoryService.getAllCategory();
			errorFlag = true;
			model.addAttribute("errorNewsTitle", "Tiêu đề không được để trống");		
			model.addAttribute("dataUpdate", newsModel);
			model.addAttribute("categories", list);
		} else if (newsModel.getNewsTitle().length() <= 5) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorNewsTitle", "Tiêu đề phải lớn hơn 5 ký tự");				
			model.addAttribute("dataUpdate", newsModel);
		}
		
		if(newsModel.getContent().length() == 0) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorContent", "Nội dung không được để trống");
			model.addAttribute("dataUpdate", newsModel);
		} else if (newsModel.getContent().length() <= 15) {
			errorFlag = true;
			List<CategoryModel> list = categoryService.getAllCategory();
			model.addAttribute("categories", list);
			model.addAttribute("errorContent", "Nội dung phải lớn hơn 15 ký tự");						
			model.addAttribute("dataUpdate", newsModel);
		}
		
		if(errorFlag) {
			return "admin_update_form";	
		}
		
		if(newsModel.getThumbnailFile().isEmpty()) {
			newsModel.setNewsId(newsId);
			newsModel.setPUBLISH_DATE(new Date());
			newsService.save(newsModel);
		} else {
			try {
				String fileName = fileUploadService.uploadImage(newsModel.getThumbnailFile());
				newsModel.setPhotoThumbnail(fileName);
			} catch(IOException e) {
				e.printStackTrace();
			}
			newsModel.setNewsId(newsId);
			newsModel.setPUBLISH_DATE(new Date());
			newsService.save(newsModel);			
		}
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		return "redirect:/admin/dashboard";
	}
	
	@PostMapping("/category/save")
	public String insertCategory(@ModelAttribute("category") CategoryModel categoryModel, RedirectAttributes redirectAttributes) {
		categoryService.save(categoryModel);
		redirectAttributes.addFlashAttribute("message", "Đã thêm");
		return "redirect:/admin/category";
	}
	
}
