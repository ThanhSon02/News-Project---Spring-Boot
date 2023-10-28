package com.example.demo.model;


import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SON_NEWS")
@NoArgsConstructor
@AllArgsConstructor

public class NewsModel {
	
	@Column(name = "NEWS_ID")
	@Id
	@SequenceGenerator(name = "news_seq", sequenceName = "SON_NEWS_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq")
	private int newsId;
	
	@Column(name = "NEWS_TITLE")
	private String newsTitle;
	
	@Column(name = "CONTENT")
	@Lob
	private String content;
	
	@Column(name = "PHOTO_THUMBNAIL")
	private String photoThumbnail;
	
	@Column(name = "VIEW_COUNT")
	private Long view = (long) 0;
	
	@Transient
	private MultipartFile thumbnailFile;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryModel category;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date PUBLISH_DATE;
	
	@PrePersist
	private void onCreate() {
		PUBLISH_DATE = new Date();
	}
	
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public String getPhotoThumbnail() {
		return photoThumbnail;
	}



	public void setPhotoThumbnail(String photoThumbnail) {
		this.photoThumbnail = photoThumbnail;
	}



	public Long getView() {
		return view;
	}



	public void setView(Long view) {
		this.view = view;
	}



	public CategoryModel getCategory() {
		return category;
	}



	public void setCategory(CategoryModel category) {
		this.category = category;
	}



	public Date getPUBLISH_DATE() {
		return PUBLISH_DATE;
	}



	public void setPUBLISH_DATE(Date pUBLISH_DATE) {
		PUBLISH_DATE = pUBLISH_DATE;
	}



	public int getNewsId() {
		return newsId;
	}



	public MultipartFile getThumbnailFile() {
		return thumbnailFile;
	}



	public void setThumbnailFile(MultipartFile thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}

	@Override
	public String toString() {
		return "NewsModel [newsId=" + newsId + ", newsTitle=" + newsTitle + ", content=" + content + ", photoThumbnail="
				+ photoThumbnail + ", view=" + view + ", thumbnailFile=" + thumbnailFile + ", category=" + category
				+ ", PUBLISH_DATE=" + PUBLISH_DATE + "]";
	}
}
