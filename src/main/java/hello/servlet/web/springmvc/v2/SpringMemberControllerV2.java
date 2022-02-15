package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    // "/springmvc/v2/members"
    @RequestMapping
    public ModelAndView members() {

        List<Member> members = memberRepository.findAll();
        // ModelView 가 아닌 ModelAndView
        ModelAndView mv = new ModelAndView("members");
        // ModelAndView 에 추가
        mv.addObject("members", members);

        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt( request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // ModelView 가 아닌 ModelAndView 사용
        ModelAndView mv = new ModelAndView("save-result");
        // ModelAndView 에 추가
        mv.addObject("member", member);

        return mv;
    }

}
