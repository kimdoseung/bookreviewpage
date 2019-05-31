package com.books.controller.book;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.books.exception.DeleteFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.member.Bookmark;
import com.books.model.domain.member.Member;
import com.books.model.service.member.BookmarkService;
import com.sun.media.jfxmedia.logging.Logger;

@Controller
public class BookmarkController {
	@Autowired
	BookmarkService bookmarkService;

	@RequestMapping(value = "/bookmark/insert/{isbn}", method = RequestMethod.GET)
	@ResponseBody
	public String insert(HttpServletRequest request, @PathVariable("isbn") String isbn) {
		System.out.println("bookmarkController���� ���� insert �޼���");
		Member member = (Member) request.getSession().getAttribute("member");
		Bookmark bookmark = new Bookmark();
		bookmark.setMember(member);
		bookmark.setIsbn(isbn);
		Bookmark result = bookmarkService.check(bookmark);
		if (result == null) { // ��ϵ� �� ������ �߰�
			bookmarkService.insert(bookmark);
			return "{\"resultCode\":1, \"msg\":\"�ϸ�ũ ��� ����\"}";
		} else {
			return "{\"resultCode\":0, \"msg\":\"�̹� ��ϵ� ���� �Դϴ�.\"}";
		}
	}

	@RequestMapping(value = "/bookmark/delete/{isbn}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request, @PathVariable("isbn") String isbn) {
		Member member = (Member) request.getSession().getAttribute("member");
		Bookmark bookmark = new Bookmark();
		bookmark.setMember(member);
		bookmark.setIsbn(isbn);
		Bookmark result = bookmarkService.check(bookmark);

		bookmarkService.delete(result.getBookmark_id());
		return "{\"resultCode\":1, \"msg\":\"�ϸ�ũ ���� ����\"}";
	}

	@ExceptionHandler(RegistFailException.class)
	@ResponseBody
	public String insertFail(RegistFailException e) {
		return "{\"resultCode\":0, \"msg\":\"" + e.getMessage() + "\"}";
	}
	
	@ExceptionHandler(DeleteFailException.class)
	@ResponseBody
	public String insertFail(DeleteFailException e) {
		return "{\"resultCode\":0, \"msg\":\"" + e.getMessage() + "\"}";
	}
}
