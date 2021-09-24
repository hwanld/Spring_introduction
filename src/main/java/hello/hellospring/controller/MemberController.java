package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    //private final MemberService memberService = new MemberService();
    //위 처럼 사용하면 여러 개의 MemberService 객체가 생성되는데, 굳이 여러 개를 만들 필요가 없음
    //따라서 하나를 생성하고 spring controller에 등록한다
    private final MemberService memberService;

    //alt+insert 단축키->constructor, getter
    @Autowired //@Autowired를 통해서 MemberController가 생성될 때 springBean에 있는 memberService를 자동으로 넣어줌 ; Dependency Injection
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
