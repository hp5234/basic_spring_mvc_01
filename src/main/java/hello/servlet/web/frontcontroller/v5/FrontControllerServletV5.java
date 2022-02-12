package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 핸들러 매핑정보
    // 유연한 컨트롤러를 위해 특정 Controller 가 아닌 Object 로 선언
    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    // 핸들러어댑터
    // 여러가지 어댑터 중 꺼내 쓰기위한 List
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    // 생성자 - 데이터 담기
    public FrontControllerServletV5() {

        // 핸들러 매핑정보 저장
        initHandlerMappingMap();
        // 핸들러어댑터 저장
        initHandlerAdapters();
    }

    // handlerMappingMap - 매핑정보 담기
    private void initHandlerMappingMap() {
        // v3 컨트롤러 추가
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // v4 컨트롤러 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    // handlerAdapters - 핸들러어댑터 추가
    private void initHandlerAdapters() {
        handlerAdapters.add( new ControllerV3HandlerAdapter() );
        handlerAdapters.add( new ControllerV4HandlerAdapter() );
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 핸들러 획득
        Object handler = getHandler(req);
        if(handler == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        // 핸들러어댑터 획득
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // ModelView 획득
        ModelView mv = adapter.handle(req, resp, handler);

        // 논리 이름을 물리 이름으로 변환
        String viewName = mv.getViewName();
        MyView view = viewResolve(viewName);

        view.render(mv.getModel(), req, resp);
    }

    // 핸들러에 해당하는 핸들러어댑터 조회
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if( adapter.supports(handler)){
                return adapter;
            }
        }
        // 없으면 예외처리
        throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest req) {
        // req 의 요청 uri 를 얻을 수 있다.
        String requestURI = req.getRequestURI();

        // 획득한 uri 로 HashMap 을 조회하여 컨트롤러 획득
        Object handler = handlerMappingMap.get(requestURI);
        return handler;
    }

    // 논리이름으로 물리 이름을 만들면서 MyView 를 반환
    private MyView viewResolve(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }
}
