package com.books.controller.book;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.books.common.FileManager;
import com.books.common.Pager;
import com.books.common.search.BookSearch;
import com.books.common.search.BookSerachMapping;
import com.books.exception.DeleteFailException;
import com.books.exception.EditFailException;
import com.books.model.domain.book.Book;
import com.books.model.domain.book.Review;
import com.books.model.domain.book.Score;
import com.books.model.domain.member.Member;
import com.books.model.domain.member.Searchbook;
import com.books.model.service.book.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	BookSerachMapping mapping;
	@Autowired
	BookSearch bookSearch;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private FileManager fileManager;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	Pager pager=new Pager();

	//���� ���� �������� �̵�
	@RequestMapping(value="/book/reviews/write/{isbn}",method=RequestMethod.GET)
	public ModelAndView insertPage(HttpServletRequest request,@PathVariable("isbn") String isbn) {
		System.out.println("���� �ۼ��������� �̵� ��û, isbn��"+isbn);
		List<Book> detailList = mapping.mapping((bookSearch.search(isbn)));	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/review_write");
		mav.addObject("isbn", isbn);
		mav.addObject("detailList",detailList);
		return mav;	
	}
	//���� ���� ������ �̵�
	@RequestMapping(value="/book/reviews/update/{review_id}",method=RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, @PathVariable("review_id") int review_id) {
		logger.info("������Ʈ�ѷ� �ۼ��������� �̵��ϴ� review_id�� : "+review_id);
		Review thisReview = reviewService.select(review_id);
		String isbn = thisReview.getIsbn();
		logger.info("bookDetail���� ������ detailList���� ������ isbn:"+isbn);
		List<Book> thisDetailList = mapping.mapping((bookSearch.search(isbn)));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/review_update");
		mav.addObject("thisDetailList", thisDetailList);
		mav.addObject("isbn", isbn);
		mav.addObject("thisReview",thisReview);
		return mav;
	}
	
	//���� ��ü ����
	@RequestMapping(value="/book/reviews",method=RequestMethod.GET)
	public ModelAndView selectAll(HttpServletRequest request) {
		List<Review> allReviewList = reviewService.selectAll();
		for(int i=0;i<allReviewList.size();i++) {
			List<Book> searchList = mapping.mapping(bookSearch.search(allReviewList.get(i).getIsbn()));
			Book book = searchList.get(0);
			allReviewList.get(i).setBook(book);
		}
		pager.init(request, allReviewList.size());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/reviewlist");
		mav.addObject("allReviewList",allReviewList);
		mav.addObject("pager",pager);
		return mav;
	}
	
	//index���� Ŭ���� �ֱٸ��� �󼼺���
	@RequestMapping(value="/book/reviews/{isbn}",method=RequestMethod.GET)
	public ModelAndView select(@PathVariable("isbn") String isbn) {
		List<Review> pickReviewList = reviewService.selectByIsbn(isbn);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/detail");
		mav.addObject("pickReviewList",pickReviewList);
		return mav;
	}
	
	//���� ��ü �� �ֱ�3�� ��������
	@RequestMapping(value="/book/review/summary",method=RequestMethod.GET)
	@ResponseBody
	public String selectAllReview() {
		List<Review> reviewLimitList = reviewService.selectAllWithLimit();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<reviewLimitList.size();i++) {
			Review review = reviewLimitList.get(i);
			JSONObject obj = new JSONObject();
			obj.put("review_id", review.getReview_id());
			obj.put("id", review.getMember().getId());
			obj.put("isbn", review.getIsbn());
			obj.put("title", review.getTitle());
			obj.put("content", review.getContent());
			obj.put("regdate", review.getRegdate());
			obj.put("modidate", review.getModidate());
			obj.put("img", review.getImg());
			jsonArray.add(obj);
		}
		json.put("reviewLimitList", jsonArray);
		return json.toString();
	}
	
	//���� 1�� ���
	@RequestMapping(value="/review/write",method=RequestMethod.POST)
	public String insert(Review review,HttpServletRequest request) {
		logger.info("reviewController�� review:: "+review);
		System.out.println("���信 ����Ϸ��� �̹���"+review.getMyImg());
		MultipartFile myImg = review.getMyImg();
		String filename = myImg.getOriginalFilename();
		logger.info("���ε��� �� �߰� filename :: "+filename);

		String realPath = request.getServletContext().getRealPath("/upload");
		logger.info("������Ʈ�� ���ε� ��� :: C:\\javafinal\\finalproject\\minssamLib\\WebContent\\upload");
		logger.info("���ε� ���� �ö� ������δ�:: "+realPath);
		
		File uploadingFile = null;
		
		try {
			uploadingFile = new File(realPath+"/"+filename);
			logger.info("���ε��� ���� Ǯ����:: "+uploadingFile);
			myImg.transferTo(uploadingFile);//���ε�
			filename = fileManager.renameByDate(uploadingFile, realPath);//���ϸ� ����
			logger.info("filemanager�� ��ȯ�� ���� �̸� :: "+filename);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(filename!=null) {
			review.setImg(filename);//�� dto�� ������� img�̹Ƿ�
			reviewService.insert(review);
		}
		//http://localhost:8080/book/search/detail/9788932030050, ����� �ٽ� ������
		//return "redirect:/books/bookDetail.jsp";
		String isbn = review.getIsbn();
		return "redirect:/book/search/detail/"+isbn;
	}
	
	
	
	//���� ����
	@RequestMapping(value="/review/delete/{review_id}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteReview(HttpServletRequest request, @PathVariable("review_id") int review_id) {
		logger.info("���� ������û");
		Member member = (Member)request.getSession().getAttribute("member");
		int membersMember_id = member.getMember_id();
		Review receivedReview = reviewService.select(review_id);
		int compareMember_id = receivedReview.getMember().getMember_id();
		if(membersMember_id==compareMember_id) {//���α� ���� Ȯ��			
			reviewService.delete(review_id);//������ ����
		}else {
			return "{\"result\":0,\"msg\":\"���� �ۼ� ���� �ƴմϴ�.\"}";
		}
		return "{\"result\":1,\"msg\":\"��������\"}";
	}
	
	//���� ����
	@RequestMapping(value="/review/update",method=RequestMethod.POST)
	@ResponseBody
	public String editReview(HttpServletRequest request,Review review) {
		logger.info("���� ������û");
		Member member = (Member)request.getSession().getAttribute("member");
		int membersMember_id = member.getMember_id();
		//int compareMember_id = review.getMember().getMember_id();
		Date d = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String transD = transFormat.format(d);
		StringBuffer sb = new StringBuffer();
		//if(membersMember_id==compareMember_id) {//���α� ���� Ȯ��			
			review.setModidate(transD);
			reviewService.update(review);//������ ����
			sb.append("{");
			sb.append("\"result\":1");
			sb.append("}");
//		}else {
//			sb.append("<script>");
//			sb.append("alert('���� �ۼ� ���� �ƴմϴ�.');");
//			sb.append("</script>");
//		}
		return sb.toString();
	}
	
	/*
	 * //���� ��Ϻ����û
	@RequestMapping(value="/review/list",method=RequestMethod.GET)
	public ModelAndView selectAll() {
		System.out.println("���� ��Ʈ�ѷ� ��Ϻ��� ��û");
		List<Review> reviewList = reviewService.selectAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/reviewlist");
		return mav;
	}
	 * 
	 */
	
	@ExceptionHandler(DeleteFailException.class)
	@ResponseBody
	public String deleteReviewFail(DeleteFailException e) {
		return "{\"result\":0, \"msg\":\"" + e.getMessage() + "\"}";
	}
	
	@ExceptionHandler(EditFailException.class)
	@ResponseBody
	public String editReviewFail(EditFailException e) {
		return "{\"result\":0, \"msg\":\"" + e.getMessage() + "\"}";
	}
}
