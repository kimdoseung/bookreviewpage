package com.books.model.repository.book;

import java.util.List;

import com.books.model.domain.book.Review;

public interface ReviewDAO {
	public List<Review> selectAll();
	public List<Review> selectAllWithLimit();
	public List<Review> selectByMember(int member_id);
	public List<Review> selectByIsbn(String isbn);
	public Review select(int review_id);
	public int insert(Review review);
	public int update(Review review);
	public int delete(int review_id);
	public List<Review> search(String searchWord);
}
