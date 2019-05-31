package com.books.controller.member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.books.common.SecurityBean;
import com.books.exception.EditFailException;
import com.books.exception.LoginFailException;
import com.books.exception.RegistFailException;
import com.books.model.domain.member.JoinCode;
import com.books.model.domain.member.Member;
import com.books.model.service.member.MemberService;



@Controller
//@RequestMapping("/rest")//����
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	SecurityBean security;
	
	@RequestMapping(value="/member/regist",method=RequestMethod.POST)
	public String regist(Member member) {
		
		member.setPass(security.textToHash(member.getPass()));
		//System.out.println(member.getPass());
		memberService.insert(member);

		return "redirect:/index.jsp";
	}
	@RequestMapping(value="/member/edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(Member member, HttpServletRequest request) {
		member.setPass(security.textToHash(member.getPass()));
		memberService.update(member);
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('������ �����Ǿ����ϴ�.');");
		sb.append("location.href='/member/mypage/{currentPage}';");
		sb.append("</script>");
		return sb.toString();
	}
	
	@RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(Member member, HttpServletRequest request) {
		
		String viewName=null;
		member.setPass(security.textToHash(member.getPass()));
		Member obj = memberService.loginCheck(member);
		// ���ǿ� ���!
		String prev = request.getHeader("referer");
		Date d = new Date();
		java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		
		if(obj==null) {
			viewName="member/login/loginfail";
			
		}else {
			request.getSession().setAttribute("member", obj);
			
			member.setLastlogin(sqlDate);
			memberService.lastLogin(member);
			viewName="redirect:"+prev;
		}	
		return viewName;
	}
	
	@RequestMapping(value="/member/logout", method = RequestMethod.GET)
	@ResponseBody
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('�α׾ƿ� �Ǿ����ϴ�.');");
		sb.append("location.href='/';");

		sb.append("</script>");
		return sb.toString();
	}
	
	
	@RequestMapping(value="/member/modify",method=RequestMethod.GET)
	public ModelAndView select(int member_id) {
		//System.out.println("�Ѿ�� member_id:" + member_id);
		Member member = memberService.select(member_id);
		ModelAndView mav = new ModelAndView("member/detail");
		mav.addObject("member",member);
		return mav;
	}
	
	@RequestMapping(value="/rest/member/idCheck", method = RequestMethod.POST)
	@ResponseBody
	public String idCheck(String id) {
		//ȸ�������Ҷ� �ߺ��� üũ����
		String result;
		Member member = memberService.idCheck(id);
		//System.out.println("������̵�"+member.getId());
		if(member==null) {
			result="�ߺ�����";
			//System.out.println(result);
		}else {
			result="�ߺ�����";
			//System.out.println(result);
		}
		//System.out.println("id="+id);
		//System.out.println("member="+member);
		//System.out.println("�����°�"+result);
		return result;
	}
	
	@RequestMapping(value="/rest/member/emailCheck", method = RequestMethod.POST)
	@ResponseBody
	public String emailCheck(String email) {
		//ȸ�������Ҷ� �ߺ��� üũ����
		String result;
		//System.out.println("�̸���:"+email);
		Member member = memberService.emailCheck(email);
		//System.out.println("������̵�"+member.getId());
		if(member==null) {
			result="�ߺ�����";
			//System.out.println(result);
		}else {
			result="�ߺ�����";
			//System.out.println(result);
		}
		//System.out.println("id="+id);
		//System.out.println("member="+member);
		//System.out.println("�����°�"+result);
		return result;
	}	
	
	@RequestMapping(value="/rest/member/passCheck", method = RequestMethod.POST)
	@ResponseBody
	public String passCheck(String pass) {
		//ȸ�������Ҷ� �ߺ��� üũ����
		String result;
		pass = security.textToHash(pass);
		Member member = memberService.passCheck(pass);
		if(member==null) {
			result="��ġ��������";
		}else {
			result="��ġ��";
		}
		return result;
	}	
	
	@RequestMapping(value="/rest/member/findId", method = RequestMethod.POST)
	@ResponseBody
	public String findId(Member member) {
		//ȸ�������Ҷ� �ߺ��� üũ����
		String result;
		Member obj = memberService.findId(member);
		//System.out.println("������̵�"+member.getId());
		if(obj!=null) {
			result = obj.getId();
		}else {
			result="��ġ�ϴ� ���̵� �����ϴ�.";
		}
		//System.out.println("�����°�"+result);
		return result;
	}
	
	@RequestMapping(value="/rest/member/infoCheck", method = RequestMethod.POST)
	@ResponseBody
	public String infoCheck(Member member) {
		String result;
		Member obj = memberService.infoCheck(member);
		if(obj!=null) {
			result="���̵� ã��";
		}else {
			result="��ġ�ϴ� ������ �����ϴ�";
		}
		System.out.println("infocheck"+result);
		return result;
	}
	


	@RequestMapping(value = "/rest/member/sendMail", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public boolean sendMail(HttpSession session,Member member) {
		JoinCode joinCode= new JoinCode(); 
		Member obj = memberService.infoCheck(member);
		
        int ran = new Random().nextInt(100000) + 10000; 
        String num = String.valueOf(ran);
        System.out.println(obj);
        System.out.println(joinCode);
        joinCode.setId(obj.getId());
        joinCode.setNum(num);
        joinCode.setEmail(obj.getEmail());
        session.setAttribute("joinCode", joinCode);
        
        String subject = "��й�ȣ ��߱��� ���� ���� �ڵ� �߱� �ȳ� �Դϴ�.";
        StringBuilder sb = new StringBuilder();
        sb.append("������ ���� �ڵ�� " + num + " �Դϴ�.");
	    /** ���� ����
	     * subject ����
	     * text ����
	     * from ������ ���� �ּ�
	     * to �޴� ���� �ּ�
	     **/
        return memberService.send(subject, sb.toString(), "dosn1011@gmail.com", member.getEmail());
    }
	
	@RequestMapping(value="/rest/member/codeCheck",method=RequestMethod.POST)
	@ResponseBody
	public String codeCheck(HttpServletRequest request, HttpSession session,JoinCode clientCode) {
		String result=null;
		JoinCode sendedCode =(JoinCode) request.getSession().getAttribute("joinCode"); //��ȣ�߼۽ñ� ������ ���� ��ü
		//System.out.println("���������� ������"+clientCode.getNum());
		//System.out.println("���ǿ� �ִ���"+sendedCode.getNum());
		//����ڰ� �Ѱ��� ���̵�� �߱޹��� ������ȣ�� ���� ���ǿ� ����ִ� ���� �� 
		if( clientCode.getId().equals(sendedCode.getId())) {
			//��ȣ ������ ���� 
			if(clientCode.getNum().equals(sendedCode.getNum())) {
				
				session.setAttribute("id", clientCode.getId());
				session.setAttribute("email", clientCode.getEmail());
				result="��ġ��";
			}		
		}
		
		
		return result;
	}

	@RequestMapping(value="/rest/member/resetPass",method=RequestMethod.POST)
	@ResponseBody
	public String resetPass(Member member) {
		//System.out.println(member.getId()+"���̵�");
		//System.out.println(member.getEmail()+"�̸���");
		//System.out.println(member.getPass()+"��й�ȣ");
		member.setPass(security.textToHash(member.getPass()));
		String result=null;
		int num=0;
		
		num=memberService.resetPass(member);
		
		if(num==1) {
			result="��й�ȣ�� ����Ǿ����ϴ�";
		}else {
			result="�Է������� Ȯ�����ּ���";
		}
			
		return result;
	}
	
	
	@ExceptionHandler(RegistFailException.class)
	@ResponseBody
	public String handleRegistFail(RegistFailException e) {
		
		return "{\"resultCode\":0,\"msg\":\""+e.getMessage()+"\"}";
	}

	@ExceptionHandler(LoginFailException.class)
	@ResponseBody
	public String handleLoginFail(LoginFailException e) {
		
		return "{\"resultCode\":0,\"msg\":\""+e.getMessage()+"\"}";
	}
	
	@ExceptionHandler(EditFailException.class)
	@ResponseBody
	public String handleEditFail(EditFailException e) {
		
		return "{\"resultCode\":0,\"msg\":\""+e.getMessage()+"\"}";
	}
}



