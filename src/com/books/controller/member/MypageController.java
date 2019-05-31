package com.books.controller.member;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.books.common.Pager;
import com.books.common.member.Admin;
import com.books.common.search.BookSearch;
import com.books.common.search.BookSerachMapping;
import com.books.model.domain.member.Bookmark;
import com.books.model.domain.member.Member;
import com.books.model.service.member.BookmarkService;

@Controller
public class MypageController {

	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private BookSerachMapping mapping;
	@Autowired
	private BookSearch bookSearch;
	@Autowired
	private Admin commonAdmin;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	Pager pager=new Pager();
	
	@RequestMapping(value="/member/mypage",method=RequestMethod.GET)
	public ModelAndView markAll(HttpServletRequest request) {
		List<Bookmark> userBookmarkList=null;
		Member member = (Member) request.getSession().getAttribute("member");
		ModelAndView mav = new ModelAndView();
		try {
			userBookmarkList = bookmarkService.selectByMember(member.getMember_id());
			for(int i=0; i<userBookmarkList.size(); i++) { 
				String isbn = userBookmarkList.get(i).getIsbn();
				userBookmarkList.get(i).setBook(mapping.mapping(bookSearch.search(isbn)).get(0)); 
			}
		 pager.init(request, userBookmarkList.size());
	 			
		} catch (Exception e) {
			mav.setViewName("member/login/error2");
			return mav;
		}
		
		mav.addObject("userBookmarkList",userBookmarkList );
		mav.addObject("pager",pager);
		mav.setViewName("member/mypage");
		return mav;
	}

	@RequestMapping(value="/member/mypage/bookmark/{bookmark_id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteBookmark(@PathVariable("bookmark_id") int bookmark_id) {
		bookmarkService.delete(bookmark_id);
		
		return null;
	}
	
	
	// ���� ������ �̵� �뵵
	@RequestMapping(value = "/admin/main", method = RequestMethod.GET)
	public ModelAndView adminMain(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Member member = (Member) request.getSession().getAttribute("member");
		request.getSession().setAttribute("auth", member.getAuth());
		if(commonAdmin.adminCheck(member.getAuth())) { // �������� �ѹ� �� Ȯ���ؼ�
			mav.setViewName("admin/adminMain"); // �����̸� ������ ���� ��������
		}else {
			mav.setViewName("/"); // �ƴϸ� �ε����� �̵�
		}
		return mav;		
	}
}


