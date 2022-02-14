package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        // servletmvc 의 코드를 재사용
        // JSP 경로
        String viewPath = "/WEB-INF/views/new-form.jsp";
        // getRequestDispatcher() 컨트롤러에서 뷰로 이동할 때 사용
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        // dispatcher.forward() : 다른 서블릿이나 JSP로 이동할 수 있는 기능이다.
        dispatcher.forward(request,response); // forward()를 통해 jsp 호출
        */
        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
