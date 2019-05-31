package com.books.model.repository.member;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.books.model.domain.member.Member;

@Repository
public class MybatisMemberDAO implements MemberDAO{

	@Autowired
	SqlSessionTemplate sessionTemplate;
	
	public List<Member> selectAll() {
		return sessionTemplate.selectList("Member.selectAll");
	}

	public List<Member> selectByAuth(int auth_id) {
		return sessionTemplate.selectList("Member.selectByAuth", auth_id);
	}

	public Member select(int member_id) {
		return sessionTemplate.selectOne("Member.select", member_id);
	}

	public int insert(Member member) {
		return sessionTemplate.insert("Member.insert", member);
	}

	public int update(Member member) {
		return sessionTemplate.update("Member.update", member);
	}

	public int delete(int member_id) {
		return sessionTemplate.delete("Member.delete", member_id);
	}

	public Member loginCheck(Member member) {
		return sessionTemplate.selectOne("Member.loginCheck", member);
	}

	public Member idCheck(String id) {
		return sessionTemplate.selectOne("Member.idCheck", id);
	}

	public Member emailCheck(String email) {
		return sessionTemplate.selectOne("Member.emailCheck", email);
	}

	public Member passCheck(String pass) {
		return sessionTemplate.selectOne("Member.passCheck",pass);
	}

	public Member findId(Member member) {
		return sessionTemplate.selectOne("Member.findId", member);
	}

	public int resetPass(Member member) {
		return sessionTemplate.update("Member.resetPass",member);
	}

	public Member infoCheck(Member member) {
		return sessionTemplate.selectOne("Member.infoCheck", member);
	}
	
	public int updateAuth(Member member) {
		return sessionTemplate.update("Member.updateAuth", member);
	}

	public int lastLogin(Member member) {
		return sessionTemplate.update("Member.lastLogin",member);
	}

	public Member selectById(String id) {
		return sessionTemplate.selectOne("Member.selectById",id);
	}
	
	public List<Member> search(String searchWord) {
		return sessionTemplate.selectList("Member.search", searchWord);
	}

}
