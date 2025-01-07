package codingon.spring_boot_default.controller._02_restapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestController {

    // ======== Template 렌더링 =========
    @GetMapping("/")
    public String getReq() {return "_02_restapi/req"; }

    // ======== Get 요청 =========
    // 매게변수 넘겨받는 법
    // 1. /test?id=123
    // 2. /test/123
    @GetMapping("/get/res1")
    public String getRes1(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age, Model model) {
        // @RequestParam 어노테이션
        // - string query 중에서 name key 에 대한 value 를 String name 에 매핑 (?key=value)
        // - required=true 가 기본 값이므로 요청 URL 에서 name key 를 필수로 보내야 함
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    } // O

    @GetMapping("/get/res2")
    public String getRes2(@RequestParam(value = "name", required = false) String name, Model model) {
        // required = false 옵션
        // - query string 에서 특정 key 를 optional 하게 받아야 하는 경우
        model.addAttribute("name", name); // null
        return "_02_restapi/res";
    } // O

    @GetMapping("/get/res3/{param1}/{param2}")
    public String getRes3(@PathVariable String param1, @PathVariable("param2") int age, Model model) {
        // @PathVariable 어노테이션
        // - test/{id} 현식의 URL 경로로 넘어오는 값을 변수로 받을 때 사용
        // - 기보적으로 경로 변수는 값을 가져야 함 (값이 없는 경우 404 에러 발생)
        // 참고. uri 에 기입한 변수명과 다른 매게변수 이름을 사용하고 싶다면
        // @PathVariable(value = "param2") int age

        model.addAttribute("name", param1);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }
    // 선택적으로 받아오는 PathVariable 이 있다면 경롤르 여러개 설정해야 함
    @GetMapping("/get/res4/{param1}/{param2}")
    public String getRes4(@PathVariable String name, @PathVariable(required = false) Integer age, Model model) {
        // required = false
        // - name (필수), age (옵션)
        // - optional 한 값은 parameter 가 있다면 맨 뒤에 오도록 설정
        // 참고. Integer age 라고 지정한 이유
        // - age 는 optional 한 값. null 이 될 수도 있기 때문에 primitive type 이 아닌 reference type 인 래퍼 객체 사용
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }

    // 실습
    @GetMapping("/introduce/{name}")
    public String introduce(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "_02_restapi/res";
    }
    @GetMapping("/introduce")
    public String getRes5(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }

    // ======== Post 요청 =========
    // - Post 로 전달할 때 controller 에서 받는 방법은 @RequestParam
    @PostMapping("/post/res1")
    public String postRes1(@RequestParam String name, @RequestParam int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }

    @PostMapping("/post/res2")
    public String postRes2(@RequestParam String name, @RequestParam(required = false) Integer age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }

    // ㄴ 여기까지 코드는 return 이 항상 Template View! 만약에 API에서 데이터 자체를 응답하고 싶다면?
    // => @ResponseBody 사용
    @PostMapping("/post/res3")
    @ResponseBody
    public String postRes3(@RequestParam String name, @RequestParam int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "_02_restapi/res";
    }
    @PostMapping("/post/res4")
    public String postRes4(@RequestParam String name,
                           @RequestParam String gender,
                           @RequestParam String birthDate,
                           @RequestParam(value = "interests", required = false) String[] interests,
                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("interests", interests);
        return "_02_restapi/res";
    }

}