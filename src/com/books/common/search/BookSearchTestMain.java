package com.books.common.search;

import java.util.List;

import com.books.model.domain.book.Book;

public class BookSearchTestMain {
	public static void main(String[] args) {
		BookSearch bookSearch = new BookSearch();
		BookSerachMapping mapping = new BookSerachMapping();
		
		String resultJSON = bookSearch.search("������");
		//System.out.println(bookSearch.search("�ظ�����"));
		//System.out.println(bookSearch.search("���ڿ���", 15));
		//System.out.println(bookSearch.search("���ڿ���", 20, 30));
		//System.out.println(bookSearch.search("9791186900529"));
		
		System.out.println(resultJSON);
		List<Book> bookList = mapping.mapping(resultJSON);
	}
}
