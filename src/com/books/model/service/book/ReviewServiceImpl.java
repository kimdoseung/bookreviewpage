package com.books.model.service.book;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.exception.DeleteFailException;
import com.books.exception.EditFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.book.Review;
import com.books.model.repository.book.ReviewDAO;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewDAO reviewDAO;
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	public List<Review> selectAll() {
		return reviewDAO.selectAll();
	}
	public List<Review> selectAllWithLimit() {
		return reviewDAO.selectAllWithLimit();
	}

	public List<Review> selectByMember(int member_id) {
		return reviewDAO.selectByMember(member_id);
	}

	public List<Review> selectByIsbn(String isbn) {
		return reviewDAO.selectByIsbn(isbn);
	}

	public Review select(int review_id) {
		return reviewDAO.select(review_id);
	}

	public void insert(Review review) throws RegistFailException {
		System.out.println("리뷰서비스impl 등록메서드 요청");
		System.out.println("리뷰서비스impl 에서 확인한 isbn :: "+review.getIsbn());
		int result = reviewDAO.insert(review);
		if (result == 0) {
			throw new RegistFailException("리뷰 등록 실패");
		}
	}

	public void update(Review review) throws EditFailException {
		logger.info("reviewServiceImpl에서의 리뷰 업데이트 요청");
		int result = reviewDAO.update(review);
		if (result == 0) {
			throw new EditFailException("리뷰 수정 실패");
		}
	}

	public void delete(int review_id) throws DeleteFailException {
		int result = reviewDAO.delete(review_id);
		if (result == 0) {
			throw new DeleteFailException("리뷰 삭제 실패");
		}
	}

	public List<Review> search(String searchWord) {
		return reviewDAO.search(searchWord);
	}
}
