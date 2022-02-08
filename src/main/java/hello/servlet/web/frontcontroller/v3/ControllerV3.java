package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    // v3 컨트롤러는 서블릿 기술들에 종속적이지 않다.

    ModelView process(Map<String, String> paramMap);

}
