<%@page import="com.books.common.Pager"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	List<Member> memberList = (List)request.getAttribute("memberList");
	Pager pager = (Pager) request.getAttribute("pager");
	List<Auth> authList = (List)request.getAttribute("authList");
%>
<!DOCTYPE html>
<html>
<!-- head start -->
<head>
<%@include file="/include/head.jsp" %>
<style>
.page_href{
	display: unset;
}
</style>
<script>
function deleteMember(member_id){
	if(!confirm("삭제 하시겠습니까?")){
		return
	}
	
	$.ajax({
		url:"/admin/member/"+member_id,
		type:"delete",
		success:function(result){
			var json = JSON.parse(result);
			if(json.resultCode == 0){
				alert(json.msg);
			}
			location.reload(true);
		}
	})
}

function updateAuth(member_id, button){
	var trTag = button.parentElement.parentElement; // tr 태그
	var auth_id = trTag.childNodes[15].children[0].value;
	$.ajax({
		url:"/admin/member/"+member_id+"/"+auth_id,
		type:"put",
		success:function(){
			location.reload(true);
		}
	});
	
}

function memberSearch(){
	   var data = $("#memberSearch").val();
	   if(data == ""){
		   location.href = '/admin/member/page';
	   }else{
	      location.href = '/admin/member/page?currentPage=1&search='+data;
	   }   
	}

function memberSearchKey(){
   if(event.keyCode == 13){
	   memberSearch();
   } 
}
</script>
<title>관리자용 페이지 - 멤버 관리</title>
</head>
<!-- head end -->
<body>
	
    <!-- header start -->
	<%@include file="/include/header.jsp"%>
	<!-- header end -->
	<!-- page visual -->
    <div class="bg_basic lecture-cart-header">
	    <div class="wrap">
	        <h2 class="red">관리자용 페이지 입니다</h2> <!-- common.css .red 밑에 .white 추가해서 변경했음 -->
	    </div>
    </div>
	<!-- blog list start -->
	
	<div class="wrap main cl mypage">
	<!-- 사이드 메뉴 include -->
	<%@include file="/include/adminSide.jsp" %>
	   <div class="list-section">
	   <%if(request.getParameter("search")==null){ %>
	   		<input type="text" placeholder="검색어 입력" id="memberSearch" onkeydown="memberSearchKey()"/>
	   	<%}else{ %>
	   		<input type="text" placeholder="검색어 입력" id="memberSearch" onkeydown="memberSearchKey()" value="<%=request.getParameter("search")%>"/>
	   	<%} %>
	       <table class="table_basic my_lecture_list">
	           <thead>
	               <tr>
                       <th>순서</th>
                       <th>아이디</th>
                       <th>이름</th>
                       <th>닉네임</th>
                       <th>이메일</th>
                       <th>가입일</th>
                       <th>최종로그인</th>
                       <th>권한</th>
                       <th>비고</th>
	               </tr>
	           </thead>
	           <tbody id="container">
				<% int num = pager.getNum();%>
           		<% int curPos = pager.getCurPos(); %>
           		<%for(int i=0; i<pager.getPageSize(); i++){ %>
           			<%if(num<1) break; %>
           			<%Member mem = memberList.get(curPos++); %>
           			<tr>
	           			<td><%=num-- %></td>
	           			<td><%=mem.getId() %></td>
	           			<td><%=mem.getName() %></td>
	           			<td><%=mem.getNickname()%></td>
	           			<td><%=mem.getEmail() %></td>
	           			<td><%=mem.getRegdate().substring(0,10) %></td>
	           			<td><%=mem.getLastlogin() %></td>
	           			<td>
	           				<select name="auth">
	           					<%for(int j=0; j<authList.size(); j++){ %>
	           					<%Auth memAuth = authList.get(j);%>
	           						<option 
	           						value="<%=memAuth.getAuth_id()%>" 
	           						<%if(mem.getAuth().getAuth_id()==memAuth.getAuth_id()){ %>selected <%} %>>
	           						<%=memAuth.getAuth_name() %></option>
	           					<%} %>
	           				</select>
	           			</td>
	           			<td>
	           				<button onClick="updateAuth('<%=mem.getMember_id()%>', this)">권한수정</button>
	           				<button onClick="deleteMember('<%=mem.getMember_id()%>')">탈퇴</button>
	           			</td>
           			</tr>
           		<%} %>
           		   <tr>
						<td colspan="9">
							<%if(pager.getFirstPage()-1>0){ %>
								<% if(request.getParameter("search")==null){%>
									<a class="page_href" href="/admin/member/page?currentPage=<%=pager.getFirstPage()-1%>">[prev]</a>
								<%} else{ %>
									<a class="page_href" href="/admin/member/page?currentPage=<%=pager.getFirstPage()-1%>&search=<%=request.getParameter("search")%>">[prev]</a>
								<%} %>
							<%}else{ %>
								<a class="page_href" href="javascript:alert('첫페이지 입니다.')">[prev]</a>
							<%} %> 
							<%for(int i=pager.getFirstPage(); i<=pager.getLastPage(); i++){ %>
								<%if(i>pager.getTotalPage()) break; %>
								<%if(request.getParameter("search")==null){ %>
									<a class="page_href" href="/admin/member/page?currentPage=<%=i%>">[<%=i %>]</a>
								<%} else{ %>
									<a class="page_href" href="/admin/member/page?currentPage=<%=i%>&search=<%=request.getParameter("search")%>">[<%=i %>]</a>
								<%} %>
							<%} %>
							<%if(pager.getLastPage()+1<pager.getTotalPage()) {%>
								<%if(request.getParameter("search")==null){ %>
									<a class="page_href" href="/admin/member/page?currentPage=<%=pager.getLastPage()+1%>">[next]</a>
								<%} else{ %>
									<a class="page_href" href="/admin/member/page?currentPage=<%=pager.getLastPage()+1%>&search=<%=request.getParameter("search")%>">[next]</a>
								<%} %>
							<%}else{ %>
								<a class="page_href" href="javascript:alert('마지막 페이지입니다.')">[next]</a>
							<%} %>
						</td>
					</tr>
	           </tbody>
	       </table>
	       
	   </div>

	</div>
	
	<!-- footer start -->
	<%@include file="/include/footer.jsp" %>
	<!-- footer end -->

</body>
</html>       