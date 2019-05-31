package com.books.controller.admin;

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
import com.books.model.domain.member.Auth;
import com.books.model.service.member.AuthService;

@Controller
public class AdminAssignController {
	@Autowired
	AuthService authService;
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	// ������ �̵�
    @RequestMapping(value = "/admin/assign/page", method = RequestMethod.GET)
    public ModelAndView showAuth(HttpServletRequest request) {
    	logger.trace("���� ���� �������� �̵�");
        ModelAndView mav = new ModelAndView("admin/admin_assign");
        mav.addObject("authList", authService.selectAll());
        return mav;
    }
    
    // ��ü ����Ʈ ��ȯ(json)
    @RequestMapping(value = "/admin/assign", method = RequestMethod.GET)
    @ResponseBody
    public List<Auth> showAuthList(HttpServletRequest request) {
        return authService.selectAll();
    }
    
    // �߰�
    @RequestMapping(value="/admin/assign", method=RequestMethod.POST)
    @ResponseBody
    public String insertAuth(Auth auth) {
    	auth.setAuth_name("�ű� �߰�");
    	authService.insert(auth);
    	return null;
    }
    
    // ����
    @RequestMapping(value = "/admin/assign/{auth_id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateAuth(Auth auth) {
    	authService.update(auth);
    	return "{\"resultCode\":1, \"msg\":\"���� ���� ����\"}";
    }
    
    // ����
    @RequestMapping(value="/admin/assign/{auth_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAuth(@PathVariable("auth_id") int auth_id) {
    	authService.delete(auth_id);
    	return "{\"resultCode\":1, \"msg\":\"���� ���� ����\"}";
    }
}
