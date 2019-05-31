package com.books.model.domain.book;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.books.model.domain.member.Member;

public class Review {
	private int review_id;
	private Member member;
	private Book book;
	private String isbn;
	private String title;
	private String content;
	private String regdate;
	private String modidate;
	private String img;
	private MultipartFile myImg;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	//getter and setter
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getModidate() {
		return modidate;
	}
	public void setModidate(String modidate) {
		this.modidate = modidate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public MultipartFile getMyImg() {
		return myImg;
	}
	public void setMyImg(MultipartFile myImg) {
		this.myImg = myImg;
	}
	
	
		
}
