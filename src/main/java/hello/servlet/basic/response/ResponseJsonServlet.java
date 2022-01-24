package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    // 객체를 JSON 형태로 변환해줄 ObjectMapper
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Content-Type 을 먼저 잡아주어야 한다.
        // Content-Type : application/json
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        // 만들어둔 JSON 객체를 사용
        HelloData helloData = new HelloData();
        helloData.setUsername("jeon");
        helloData.setAge(20);

        // 객체를 JSON 형태로 변환 - ObjectMapper 사용
        String result = objectMapper.writeValueAsString(helloData);

        // 응답
        resp.getWriter().write(result);
    }
}
