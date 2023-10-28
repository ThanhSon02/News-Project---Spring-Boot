package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CategoryModel;
import com.example.demo.model.NewsModel;
import com.example.demo.repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	@Transactional
	public List<NewsModel> findAllNews() {
		return newsRepository.findAll();
	}
	
	
	public Page<NewsModel> newsPage(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return newsRepository.findAll(pageable);
	}
	
	@Transactional
	public Page<NewsModel> findByCategory(CategoryModel categoryModel, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return newsRepository.findByCategory(categoryModel, pageable);
	}
	
	public NewsModel getNews(int newsId) {
		return newsRepository.getReferenceById(newsId);
	}
	
	// Get Detail and increase view
	public NewsModel getDetail(int newsId) {
		NewsModel model = newsRepository.findById(newsId).get();
		
		// Count View
		Long currentView = model.getView();
		model.setView(currentView + 1);
		newsRepository.save(model);
		
		return model;
	}
	
	// save news
	public NewsModel save(NewsModel newsModel) {
			newsRepository.save(newsModel);
			return newsModel;			
	}
	
	// delete news
	public void deleteNews(int newsId) {
		newsRepository.deleteById(newsId);			
	}
	
	// search news
	public Page<NewsModel> searchNews(String keyword, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return newsRepository.findByKeyword(keyword, pageable);
	}
	
	// get list view desc
	public Page<NewsModel> listViewDesc(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return newsRepository.findListViewDesc(pageable);
	}
}
