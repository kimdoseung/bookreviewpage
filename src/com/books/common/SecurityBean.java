package com.books.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

//������ �����ͺ��̽��� ��й�ȣ�� �������� �Ϲ� �ؽ�Ʈ�� ��������, ���� ������ ��ȣȭ�� �����ͷ� �־��!
@Component
public class SecurityBean {
	//�Ϲ��ؽ�Ʈ�� �ؽð�(16������)���� ��ȯ�Ͽ� ��ȯ�ϴ� �޼��� ����
	public String textToHash(String password) {
		StringBuilder sb = new StringBuilder();
		try {
			//���� ����� �˰��� �����ϱ�!!
			//�˰����� ����: 224,256,384,512
			MessageDigest md=MessageDigest.getInstance("SHA-256");//�̱������θ��ø��Ӥ����ִ�
			md.update(password.getBytes());//�ؽ�ȭ ��ų �����͸� ����Ʈȭ(�� �ɰ��� �־������)
			//��ȣȭ�� ����Ʈ ��ȯ�ޱ�
			byte[] data=md.digest();//�Ϲݹ���Ʈ �迭�� ��ȣȭ���Ѽ� ��ȣȭ�� �����ͷ� ��ȯ
			//�迭�� ����ϱ⿣ ������ �����Ƿ�, ��Ʈ��ȭ ��Ű��
			for(int i=0;i<data.length;i++) {
				sb.append(Integer.toString((data[i]&0xff)+0x100,16).substring(1));
			}
			//System.out.println("�����Ⱦ�ȣ�� ���̴�: "+sb.toString().length());
			//System.out.println("������� ��ȣ��: "+sb.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	public static void main(String[] args) {
		SecurityBean sb = new SecurityBean();
		sb.textToHash("apple");
	}
}















