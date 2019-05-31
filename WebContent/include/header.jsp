<%@page import="com.books.common.member.Admin"%>
<%@page import="com.books.model.domain.member.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%! Admin chkAdmin = new Admin();%>
<%
   Member member = (Member)session.getAttribute("member");
%>

<script>

// 검색 페이지로 이동
function search(){
   var data = $("#bookSearch").val();
   if(data == ""){
      alert("검색어를 입력 해 주세요");
   }else{
      location.href = '/book/search/'+data+'/1';
   }   
}

function searchKeyDown(){
   if(event.keyCode == 13){
      //alert("엔터!!");
      search();
   } 
}
</script>
<header>
   <div class="title-bar wrap cl">
      <div class="menu-btn">
         <i class="fa fa-bars" aria-hidden="true"></i>
      </div>
      <!-- LOGO start -->
      <h1 class="logo">
         <a href="/">min<span>ssam's</span><span>Library</span></a>
      </h1>
      <!-- LOGO end -->
      <!-- search box -->
      <div class="searchArea" >
        <p class="search-input">
        	<input type="text" name="test" id="bookSearch" placeholder="찾는 도서명 입력" onkeydown="searchKeyDown()"/>
        </p>
       	<p class="search-btn" onclick="search()">
            <button type="button">
            	<img src="/asset/images/search.png" alt="검색버튼 이미지"/>
        	</button>
      	</p>
      </div>
      <!-- search box ends -->
      <!-- title-bar-con-menu -->
      <div class="title-icon-wrapper">
         <!-- login btn start -->
         <i class="far fa-user dropdown"></i>
         <div class="user-content">
            <%if (member != null) { %>
            <ul>
               <li><a href="/member/mypage" class="user-btn">마이페이지</a></li>
               <li><a href="/payment/cart/" class="user-btn">장바구니</a></li>
               <li><a href="/mypage/wish/" class="user-btn">찜목록</a></li>
               <li><a href="/member/logout" class="user-btn" id="loginout-bt">로그아웃</a></li>
         <%}else{ %>
               <li><a href="#" class="user-btn" id="login-bt">로그인&nbsp;/&nbsp;회원가입</a></li>               
         <%} %>
            </ul>
         </div>
         <!-- login btn end -->
         <%if(member!=null){ %>
         <!-- member welcome -->
         	<span class="welcomeMan"><%=member.getName() %>님 환영합니다.</span>
         	<script>
         		$(".searchArea").addClass("SessionTrueSearch");
         	</script>
         <%}else{%>
         	<script>
         		$(".searchArea").removeClass("SessionTrueSearch");
         	</script>
         <%} %>
      </div>
      <!-- title-bar-con-menu-end -->
   </div>
   <!-- modal wrapper start -->
   <div id="wrapper"></div>
   <!-- modal wrapper end -->

   <!-- Main menu start -->
   <div id="main-menu">
      <nav class="main-menu">
         <div class="main-top">
            <div class="regist1">
            
               <div class="cl">
                  <span>로그인 해주세요.</span> <a href="#">로그인</a> <a href="#">회원가입</a>
               </div>
            </div>
            
            <ol class="regist2 cl">
               <li onclick="location.href='../order/cart.html'"><i
                  class="cart"></i>장바구니</li>
               <li onclick="location.href='../mypage/wish_list.html'"><i
                  class="heart"></i>위시리스트</li>
               <li><i class="user"></i>마이페이지</li>
            </ol>
         </div>
         <!-- nav menus -->
         <div class="menu-item"
            onclick="location.href='/book/search/ /1'">도서 검색</div>
         <div class="menu-item"
            onclick="location.href='/book/reviews'">평가/리뷰
            게시판</div>
         <div class="menu-item" onclick="location.href='/book/popular'">인기도서 목록</div>
         <%if (session.getAttribute("member") != null) { %>
         <div class="menu-item" onclick="location.href='/member/mypage'">MyPage</div>
         	<!-- admin 체크해서 Admin이면 표시 -->
	         <%if (chkAdmin.adminCheck(((Member) session.getAttribute("member")).getAuth())) { %>
	         	<div class="menu-item" onclick="location.href='/admin/main'">관리자 페이지</div>
	         <%} %>
         <%} %>

         <!-- nav menus end-->
      </nav>
   </div>
   <!-- Main menu end -->
</header>
<!-- login modal -->
<div id="full"></div>
<div id="full2"></div>
<div id="view"></div>