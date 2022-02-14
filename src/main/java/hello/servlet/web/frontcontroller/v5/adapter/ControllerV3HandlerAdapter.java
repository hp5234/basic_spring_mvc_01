package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    // 핸들러를 지원할 수 있는지 여부를 반환
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    // 핸들러를 호출하여 ModelView 를 반환
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // Object 로 받았기때문에 ControllerV3 로 캐스팅
        // V3 로 캐스팅하는것이 안전한가는 앞에서 supports 를 통해 한번 검사하기때문에 안전하다고 본다.
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);

        // 핸들러(컨트롤러)에서 결과를 가져옴
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    // req 객체에서 파라미터를 분리하여 Map 형태로 반환
    private Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName-> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}

