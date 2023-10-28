package com.example.demo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CategoryModel;
import com.example.demo.model.NewsModel;

@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Integer>{
	Page<NewsModel> findByCategory(CategoryModel category, Pageable pageable);

	@Query("SELECT n FROM NewsModel n where n.newsTitle LIKE %?1%")
	Page<NewsModel> findByKeyword(String newsTitle, Pageable pageable);
	
//	@Query("SELECT n FROM NewsModel n ORDER BY n.view DESC")
	@Query("SELECT n FROM NewsModel n WHERE n.view > 10")
	Page<NewsModel> findListViewDesc(Pageable pageable);
}
