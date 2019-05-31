package com.books.model.service.member;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.books.exception.DeleteFailException;
import com.books.exception.EditFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.member.Member;
import com.books.model.repository.member.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	@Qualifier("mybatisMemberDAO")
	private MemberDAO memberDAO;

	@Autowired
	private JavaMailSender javaMailSender;

	
	public List<Member> selectAll() {
		return memberDAO.selectAll();
	}

	public List<Member> selectByAuth(int auth_id) {
		return memberDAO.selectByAuth(auth_id);
	}

	public Member select(int member_id) {
		return memberDAO.select(member_id);
	}

	public void insert(Member member) throws RegistFailException {

		int result = memberDAO.insert(member);
		if (result == 0) {
			throw new RegistFailException("������ Ȯ�����ּ���");
		}
	}

	public void update(Member member) {
		int result = memberDAO.update(member);
		if (result == 0) {
			throw new EditFailException("������ �����Ͽ����ϴ�");
		}
	}

	public void delete(int member_id) {
		int result = memberDAO.delete(member_id);
		if (result == 0) {
			throw new DeleteFailException("������ �����Ͽ����ϴ�");
		}
	}

	public Member loginCheck(Member member) {
		
		return memberDAO.loginCheck(member);
	}

	public Member idCheck(String id) {
		return memberDAO.idCheck(id);
	}

	public Member emailCheck(String email) {
		return memberDAO.emailCheck(email);
	}

	public Member passCheck(String pass) {
		return memberDAO.passCheck(pass);
	}
	
	public Member findId(Member member) {
		return memberDAO.findId(member);
	}

	public int resetPass(Member member) {
		int result = memberDAO.resetPass(member);
		if (result == 0) {
			throw new EditFailException("������ �����Ͽ����ϴ�");
		}
		return result;
	}

	@Override
	public Member infoCheck(Member member) {
		return memberDAO.infoCheck(member);
	}

	@Override
	public boolean send(String subject, String text, String from, String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
 
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom(from);
            helper.setTo(to);
 
 
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;

	}

	
	public void updateAuth(Member member) {
		int result = memberDAO.updateAuth(member);
		if (result == 0) {
			throw new EditFailException("���� ������ �����Ͽ����ϴ�");
		}
	}

	public void lastLogin(Member member) {
			int result = memberDAO.lastLogin(member);
			if(result==0) {
				throw new EditFailException("������ �α��α���� �����ʾҽ��ϴ�.");
			}
	}

	@Override
	public Member selectById(String id) {
		return memberDAO.selectById(id);
	}
	
	public List<Member> search(String searchWord) {
		return memberDAO.search(searchWord);
	}

}