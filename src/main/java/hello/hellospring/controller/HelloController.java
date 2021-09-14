package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello");
        return "hello";
        //templates의 hello.html으로 가서 model을 가지고 랜더링해라!
        //controller에서 return으로 문자를 반환하면 viewResolver가 화면을 찾아가서 처리한다.
        //resources::templates/+{ViewName}+.html
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name",name);
        return "hello-template";
    }
    //ctrl+p를 통해서 필요한 parameter를 확인할 수 있다.
    //http://localhost:8080/hello-mvc?name=spring!!!!!!!!!!!!!

    @GetMapping("hello-string")
    @ResponseBody //http의 body부의 데이터를 직접 넣어주겠다!
    public String helloString(@RequestParam("name") String name) {
        return "hello "+ name; //"hello spring"
    }
    //html파일을 내보내는 것이 아닌, 적혀있는 문자를 그대로 내보내는 방식_API방식

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    //객체를 내보내는 방식_api:json 방식
    //요즘은 json으로 반환하는 것이 기본이다. 앞으로의 프로젝트도 마찬가지로 json 방식을 사용하는게 맞음.
}
