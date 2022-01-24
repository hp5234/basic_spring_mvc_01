package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // helloServlet 서블릿이 호출되면 service 가 호출된다.
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // uri 정상 동작 확인
        System.out.println("HelloServlet.service");
        System.out.println("req = " + req);
        System.out.println("resp = " + resp);

        // 요청 확인
        String username = req.getParameter("username");
        System.out.println("username = " + username);

        // 응답 확인
        // 설정한 응답 구성들은 헤더 정보에 담긴다.
        resp.setContentType("text/plain"); // 응답을 구성 res 를 설정
        resp.setCharacterEncoding("utf-8"); // 응답을 구성 res 를 설정
        resp.getWriter().write("hello " + username);
    }
}
