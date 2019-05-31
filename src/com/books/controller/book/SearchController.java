/*
* å �˻��� �� ����ϴ� ��Ʈ�ѷ�
 * */
package com.books.controller.book;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.books.common.Pager;
import com.books.common.search.BookSearch;
import com.books.common.search.BookSerachMapping;
import com.books.model.domain.book.Book;
import com.books.model.domain.book.Review;
import com.books.model.domain.book.ReviewComment;
import com.books.model.service.book.ReviewCommentService;
import com.books.model.service.book.ReviewService;

@Controller
public class SearchController {
	@Autowired
	BookSerachMapping mapping;
	@Autowired
	BookSearch bookSearch;
	@Autowired
	ReviewService reviewService;
	@Autowired
	ReviewCommentService reviewCommentService;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	Pager pager = new Pager();
	
	@RequestMapping(value = "/book/search/{searchWord}/{currentPage}", method = RequestMethod.GET)
	public ModelAndView searchPage(@PathVariable("searchWord") String searchWord, @PathVariable("currentPage") String currentPage) {
		logger.trace("�˻� �ܾ� : " + searchWord);
		
		ModelAndView mav = new ModelAndView("books/bookSearchList");
		List<Book> searchList = mapping.mapping((bookSearch.search(searchWord, 10, Integer.parseInt(currentPage)*10-9)));
		if(searchList.size()>0){ // ����¡ ó��
			pager.searchInit(Integer.parseInt(currentPage), searchList.get(0).getTotal());
		}else{	// �˻� ��� ������ �׳� �ϳ��� �ִ°�ó�� ����
			pager.searchInit(1,1);
		}
		mav.addObject("searchList", searchList);
		mav.addObject("searchWord", searchWord);
		mav.addObject("pager", pager);
		return mav;
	}
	
	//bookDetail����
	@RequestMapping(value = "/book/search/detail/{isbn}", method = RequestMethod.GET)
	public ModelAndView showDetail(@PathVariable("isbn") String isbn) {
		logger.trace("SearchController���� bookDetail�̵� ��û");
		List<Book> detailList = mapping.mapping((bookSearch.search(isbn)));
		List<Review> reviewList = reviewService.selectByIsbn(isbn);
		ArrayList reviewId = new ArrayList();
		List<ReviewComment> rcList=null;
		for(int i=0;i<reviewList.size();i++) {
			reviewId.add(reviewList.get(i).getReview_id());
			rcList = (List)reviewCommentService.selectByReview((int)reviewId.get(i));
		}
		ModelAndView mav = new ModelAndView("books/bookDetail");
		mav.addObject("detailList", detailList);
		mav.addObject("reviewList", reviewList);
		mav.addObject("rcList", rcList);
		
		return mav;
	}
}
