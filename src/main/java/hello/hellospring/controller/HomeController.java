package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") //처음 페이지를 시작했을때, 아무것도 없는, 즉 시작 화면 도메인
    public String home() {
        return "home";
    }
}
