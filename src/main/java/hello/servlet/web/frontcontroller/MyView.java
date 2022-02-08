package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    // 임시로 view 를 만드는 행위를 render 한다고 표현했다.
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // jsp 는 reqest.getAttribute 를 사용하기때문에
        // model 의 데이터를 모두 꺼내야한다.

        // req 에 값을 key, value 형태로 저장
        modelToRequestAttribute(model, req);

        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req,resp);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest req) {
        // req 에 값을 key, value 형태로 저장
        model.forEach((key, value) -> req.setAttribute(key, value));
    }
}
