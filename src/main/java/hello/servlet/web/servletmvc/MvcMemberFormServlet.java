package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // JSP 경로
        String viewPath = "/WEB-INF/views/new-form.jsp";

        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        // getRequestDispatcher() 컨트롤러에서 뷰로 이동할 때 사용

        dispatcher.forward(req,resp); // forward()를 통해 jsp 호출
        // dispatcher.forward() : 다른 서블릿이나 JSP로 이동할 수 있는 기능이다.

    }
}
