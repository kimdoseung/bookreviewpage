package com.books.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AnnotationMemberSessionAspect {
	//xml�� ���⶧���� ��ġ(pointcut)�� ��(advice)�� �ڹ��ڵ忡 ����
	@Pointcut("execution(public * com.books.controller.member.MemberController..*(..))")
	public void checkMember() {}//���̵� ����
	/*
	 * @Pointcut("execution(public * com.books.controller.BoardController..*(..))")
	 * public void checkBoard() {}//
	 * 
	 * @Pointcut("execution(public * com.books.controller.ProductController..*(..))"
	 * ) public void checkProduct() {}//
	 */	//�Ʒ��� �迭�� ����ִ� ��û URL�� ���ؼ��� ����üũ�� ���ذ���
	String[] exceptList= {
		"/member/login",
		"/member/contact",//������
		"rest/member/codeCheck"
		
	};
	
	//���� �����ڵ�
	//�α����� �ʿ��� ������������ ȣ�⸸ ó���ؾ��Ѵ�
	@Around("checkMember()")
	public String loginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		//���üũ������������ ���縦 üũ�ϴ� ��쿡�� ����
		//�Ǵܱ���?? �ᱹ HttpServletRequest�� �Ѿ������ ����
		String viewName=null;
		HttpServletRequest request=null;
		int count=0;//��ܿ� ������ ��� ������Ű��!
		Object[] objArray = joinPoint.getArgs();//�޼��� ȣ��� ���޵� �Ű������� ��ȯ
		String requestURL=null;
		for(Object obj :objArray) {//��� �Ű����� ����(������Ʈ ��ü���� ����)
			 if(obj instanceof HttpServletRequest) {
				 request=(HttpServletRequest)obj;
				 requestURL=request.getRequestURL().toString();
				 System.out.println("���� ���� ��û"+requestURL);
				 requestURL.endsWith("/member/login");
				for(int i=0;i<exceptList.length;i++) {
					if(requestURL.endsWith(exceptList[i])) {
						count++;//���ܸ�� �߰�
					}
				}
			}
		}
		

		
		//�α����� �ʿ��� �޼��� ȣ��ø� ���� üũ
		if(request!=null&& count==0) {
			if(request.getSession().getAttribute("member")==null) {
				viewName="member/login/error";
				String methodName = joinPoint.getSignature().getName();
				System.out.println("�α��� �ʿ�:ȣ��� ���� �޼����"+methodName+",�޼����� ��ȯ ���� "+viewName);
			}else {
				viewName=(String)joinPoint.proceed();
				String methodName = joinPoint.getSignature().getName();
				System.out.println("�α��� �ʿ�:ȣ��� ���� �޼����"+methodName+",�޼����� ��ȯ ���� "+viewName);
			}
		}else {
			viewName=(String) joinPoint.proceed();
			String methodName = joinPoint.getSignature().getName();
			System.out.println("�α��� ���ʿ�:ȣ��� ���� �޼����"+methodName+",�޼����� ��ȯ ���� "+viewName);
		}
		
		
		return viewName;		
	}
}

