package com.books.model.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.books.exception.DeleteFailException;
import com.books.exception.EditFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.member.Bookmark;
import com.books.model.repository.member.BookmarkDAO;

@Service
public class BookmarkServiceImpl implements BookmarkService{
	
	@Autowired
	BookmarkDAO bookmarkDAO;
	
	public List<Bookmark> selectAll() {
		return bookmarkDAO.selectAll();
	}

	public List<Bookmark> selectByMember(int member_id) {
		return bookmarkDAO.selectByMember(member_id);
	}

	public List<Bookmark> selectByIsbn(String isbn) {
		return bookmarkDAO.selectByIsbn(isbn);
	}

	public Bookmark select(int bookmark_id) {
		return bookmarkDAO.select(bookmark_id);
	}

	public void insert(Bookmark bookmark) throws RegistFailException{
		int result = bookmarkDAO.insert(bookmark);
		if(result==0) {
			throw new RegistFailException("�ϸ�ũ ��� ����");
		}
	}

	public void update(Bookmark bookmark) throws EditFailException{
		int result= bookmarkDAO.update(bookmark);
		if(result==0) {
			throw new EditFailException("�ϸ�ũ ���� ����");
		}
	}

	public void delete(int bookmark_id) throws DeleteFailException{
		int result=bookmarkDAO.delete(bookmark_id);
		if(result==0) {
			throw new DeleteFailException("�ϸ�ũ ���� ����");
		}
	}

	public Bookmark check(Bookmark bookmark) {
		return bookmarkDAO.check(bookmark);
	}
}
