package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// urlPatterns 의 v2/* 에 의해 v2 하위에 어떤 경로가 들어와도 이 서블릿은 호출이 된다.
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet{

    // 매핑정보를 저장하기 위한 HaspMap
    // key 와 value 로 url 과 controller 를 설정
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 생성자가 호출될 때 url 에 맞는 컨트롤러를 저장
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // req 의 요청 uri 를 얻을 수 있다.
        String requestURI = req.getRequestURI();

        // 획득한 uri 로 HashMap 을 조회
        ControllerV2 controller = controllerMap.get(requestURI);
        if(controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        MyView view = controller.process(req, resp);
        view.render(req, resp);
    }
}
