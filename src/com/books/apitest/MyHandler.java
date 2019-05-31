package com.books.apitest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.books.model.domain.book.Book;
@Component
public class MyHandler extends DefaultHandler{
	//Ŀ�� ��ġ ���::���� ����� ��ġ�� �˱� ����
	private boolean no;
	private boolean ranking;
	private boolean bookname;
	private boolean isbn13;
	private boolean loan_count;
	private boolean bookImageURL;
	
	//dto�� �����ϰ� �� dto�� ���ʸ� Ÿ������ ���� ����Ʈ ����
	private List<PopularBookTest> popularBookList;
	

	//preparing empty dto
	PopularBookTest pb;
	
	public List<PopularBookTest> getPopularBookList(){
		return popularBookList;
	}
	public void setPopularBookList(List<PopularBookTest> popularBookList) {
		this.popularBookList = popularBookList;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		//System.out.println("=================�α⵵�� ������ ����================");
		popularBookList = new ArrayList<PopularBookTest>();
	}
	
	@Override
	public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
		//System.out.println("���� �±� ����");
		//System.out.print("<"+tag+">");
		if(tag.equals("no")) {
			pb = new PopularBookTest();
			no=true;
		}else if(tag.equals("ranking")) {
			ranking=true;
		}else if(tag.equals("bookname")) {
			bookname=true;
		}else if(tag.equals("isbn13")) {
			isbn13=true;
		}else if(tag.equals("loan_count")) {
			loan_count=true;
		}else if(tag.equals("bookImageURL")) {
			bookImageURL=true;
		}
	}
	
	//�±� ���̿� ���� ������ ���� �޼���
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch,start,length);
		//System.out.println(content);
		
		//����� ��ġ�� ���� ���빰�� dto�� ����
		if(no) {
			//System.out.println("character�޼����� no ���ǹ�����"+content);
			pb.setNo(Integer.parseInt(content));
			//System.out.println("å��ȣ"+pb.getNo());
		}else if(ranking) {
			//System.out.println("character�޼����� ranking ���ǹ�����"+content);
			pb.setRanking(content);
			//System.out.println("å����"+pb.getRanking());
		}else if(bookname) {
			//System.out.println("character�޼����� bookname ���ǹ�����"+content);
			pb.setBookname(content);
			//System.out.println("å����"+pb.getBookname());
		}else if(isbn13) {
			//System.out.println("character�޼����� isbn13 ���ǹ�����"+content);
			pb.setIsbn13(content);	
			//System.out.println("å�ڵ�"+pb.getBookname());
		}else if(loan_count) {
			//System.out.println("character�޼����� loan_count ���ǹ�����"+content);
			pb.setLoan_count(content);
			//System.out.println("å�����"+pb.getLoan_count());
		}else if(bookImageURL) {
			//System.out.println("character�޼����� bookImageURL ���ǹ�����"+content);
			pb.setBookImageURL(content);
			//System.out.println("å�̹���url"+pb.getBookImageURL());
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String tag) throws SAXException {
		//System.out.println("�ݴ� �±׸� �������");
		//System.out.println("</"+tag+">");
		
		//����� ��ġ���� ���� ���󺹱�
		if(tag.equals("no")) {
			no=false;
			popularBookList.add(pb);
			
		}
		if(tag.equals("ranking")) {
			ranking=false;
		}
		if(tag.equals("bookname")) {
			bookname=false;
		}
		if(tag.equals("isbn13")) {
			isbn13=false;
		}
		if(tag.equals("loan_count")) {
			loan_count=false;
		}
	}
	
	@Override
	public void endDocument() throws SAXException {
		//System.out.println("================������ ���Դϴ�.=====================");
		
		for(int i=0;i<popularBookList.size();i++) {
			//System.out.println("���� ��� : "+popularBookList.size());
		}
	}
}
