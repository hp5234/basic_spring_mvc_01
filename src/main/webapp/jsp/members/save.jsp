<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%
    // 저장소
    MemberRepository memberRepository = MemberRepository.getInstance();

    // form 에서 요청한 요청 데이터 획득
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age")); //req.getParameter() 의 응답결과는 항상 문자 -> 변환 필요

    // 비즈니스로직
    // 객체 생성
    Member member = new Member(username, age);
    // 저장
    memberRepository.save(member);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
