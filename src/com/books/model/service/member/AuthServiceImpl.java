package com.books.model.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.exception.DeleteFailException;
import com.books.exception.EditFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.member.Auth;
import com.books.model.repository.member.AuthDAO;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthDAO authDAO;

	public List<Auth> selectAll() {
		return authDAO.selectAll();
	}

	public Auth select(int auth_id) {
		return authDAO.select(auth_id);
	}

	public void insert(Auth auth) throws RegistFailException {
		int result = authDAO.insert(auth);
		if (result == 0) {
			throw new RegistFailException("���� ��� ����");
		}
	}

	public void update(Auth auth) {
		int result = authDAO.update(auth);

		if (result == 0) {
			throw new EditFailException("���� ���� ����");
		}
	}

	public void delete(int auth_id) {
		int result = authDAO.delete(auth_id);

		if (result == 0) {
			throw new DeleteFailException("���� ���� ����");
		}
	}

}
