package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// urlPatterns 의 v1/* 에 의해 v1 하위에 어떤 경로가 들어와도 이 서블릿은 호출이 된다.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet{

    // 매핑정보를 저장하기 위한 HaspMap
    // key 와 value 로 url 과 controller 를 설정
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 생성자가 호출될 때 url 에 맞는 컨트롤러를 저장
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // req 의 요청 uri 를 얻을 수 있다.
        String requestURI = req.getRequestURI();

        // 획득한 uri 로 HashMap 을 조회
        ControllerV1 controller = controllerMap.get(requestURI);
        if(controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        controller.process(req, resp);
    }
}
