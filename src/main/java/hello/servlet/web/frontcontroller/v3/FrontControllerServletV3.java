package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// urlPatterns 의 v3/* 에 의해 v3 하위에 어떤 경로가 들어와도 이 서블릿은 호출이 된다.
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet{

    // 매핑정보를 저장하기 위한 HaspMap
    // key 와 value 로 url 과 controller 를 설정
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 생성자가 호출될 때 url 에 맞는 컨트롤러를 저장
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // req 의 요청 uri 를 얻을 수 있다.
        String requestURI = req.getRequestURI();

        // 획득한 uri 로 HashMap 을 조회하여 컨트롤러 획득
        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        // paramMap 넘겨주어야한다.-----

        // 파라미터 데이터 획득
        Map<String, String> paramMap = createParamMap(req);

        // 파라미터 전달
        ModelView mv = controller.process(paramMap);

        // 논리 이름을 물리 이름으로 변환
        String viewName = mv.getViewName();
        MyView view = viewResolve(viewName);

        view.render(mv.getModel(), req, resp);
    }

    // 컨트롤러의 동작 레벨에 비해 디테일한 로직은 별도의 메서드로 분리
    // 논리이름으로 물리 이름을 만들면서 MyView 를 반환
    private MyView viewResolve(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }

    // 컨트롤러의 동작 레벨에 비해 디테일한 로직은 별도의 메서드로 분리
    // req 객체에서 파라미터를 분리하여 Map 형태로 반환
    private Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName-> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}
