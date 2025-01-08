package codingon.spring_boot_default.controller._02_restapi;

import codingon.spring_boot_default.dto.UserDTO;
import codingon.spring_boot_default.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestController {

    // ======== Template 렌더링 =========
    @GetMapping("/")
    public String getReq() {
        return "_02_restapi/req";
    }

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

    // 실습 1
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

    // 실습 2
    @PostMapping("/post/res7")
    public String postRes7(@RequestParam String name,
                           @RequestParam String gender,
                           @RequestParam String birthDate,
                           @RequestParam String interests,
                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("interests", interests);
        return "_02_restapi/res";
    }

    // ============== DTO 이용 ==============
    // 1. GET 요청
    @GetMapping("/dto/res1")
    @ResponseBody
    public String dtoRes1(@ModelAttribute UserDTO userDTO) {
        // 변수로 값을 하나씩 가져오는게 아니라 DTO 객체에 담아서 가져오기
        // @ModelAttribute : HTML 폼 데이터를 컨트롤러로 전달할 때 객체 매핑하는 어노테이션
        // -> 매핑 : setter 함수 실행
        return userDTO.getName() + " " + userDTO.getAge();
    } // O

    // 2. POST 요청
    @PostMapping("/dto/res2")
    @ResponseBody
    public String dtoRes2(UserDTO userDTO) {
        // @ModelAttribute 어노테이션이 없을 때에는 자동 추가됨
        return userDTO.getName() + " " + userDTO.getAge();
    } // O

    @PostMapping("/dto/res3")
    @ResponseBody
    public String dtoRes3(@RequestBody UserDTO userDTO) {
        // @RequestBody 어노테이션
        // - 요청의 본문에 있는 데이터 (req.body)를 읽어와서 객체에 매핑
        // - 여기서 매핑? 필드 값에 값을 주입
        // - 전달 받은 요청의 형식이 json or xml 타입일 때만 가능
        // "일반 폼 전송" ()
        // 따라서 @RequestBody 어노테이션 사용 시 오류 발생함
        return userDTO.getName() + " " + userDTO.getAge();
    } // X

// 실습 DTO 이용하기 – 일반 폼
//    @GetMapping("/dto/res1")
//    @ResponseBody
//    public String dtoRes1(@ModelAttribute UserDTO userDTO) {
//        System.out.println("[GET] userDTO (name) = " + userDTO.getName());
//        System.out.println("[GET] userDTO (age) = " + userDTO.getAge());
//        return userDTO.getName() + " " + userDTO.getAge();
//    }
//    @PostMapping("/dto/res2")
//    @ResponseBody
//    public String dtoRes2(UserDTO userDTO) {
//        System.out.println("[POST] userDTO (name) = " + userDTO.getName());
//        System.out.println("[POST] userDTO (age) = " + userDTO.getAge());
//        return userDTO.getName() + " " + userDTO.getAge();
//    }
//    @PostMapping("/dto/res3")
//    @ResponseBody
//    public String dtoRes22(@RequestBody UserDTO userDTO) {
//        System.out.println("[POST] userDTO (name) = " + userDTO.getName());
//        System.out.println("[POST] userDTO (age) = " + userDTO.getAge());
//        return userDTO.getName() + " " + userDTO.getAge();
//    }

    // ============== VO 이용 ==============
    // 1. GET 요청
    // vo = null (ModelAttribute => setter 함수 실행)
    // setter 함수가 없어 기본 값 출력 (null 0)
    @GetMapping("/vo/res1")
    @ResponseBody
    public String voRes1(@ModelAttribute UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge(); // null 0
    } // O (null 0)

    // 2. POST 요청
    @PostMapping("/vo/res2")
    @ResponseBody
    public String voRes2(UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge(); // null 0
    } // O (null 0)

    @PostMapping("/vo/res3")
    @ResponseBody
    public String voRes3(@RequestBody UserVO userVO) {
        return userVO.getName() + " " + userVO.getAge(); // null 0
    } // X (type=Unsupported Media Type, status=415)

//    실습. VO 이용하기 – 일반 폼
//    @GetMapping("/vo/res1")
//    @ResponseBody
//    public String voRes1(@ModelAttribute UserVO userVO) {
//        System.out.println("[GET] userVO (name) = " + userVO.getName());
//        System.out.println("[GET] userVO (age) = " + userVO.getAge());
//        return userVO.getName() + " " + userVO.getAge();
//    }
//    @PostMapping("/vo/res2")
//    @ResponseBody
//    public String voRes2(UserVO userVO) {
//        System.out.println("[POST] userVO (name) = " + userVO.getName());
//        System.out.println("[POST] userVO (age) = " + userVO.getAge());
//        return userVO.getName() + " " + userVO.getAge();
//    }
//    @PostMapping("/vo/res3")
//    @ResponseBody
//    public String voRes3(@RequestBody UserVO userVO) {
//        System.out.println("[POST] userVO (name) = " + userVO.getName());
//        System.out.println("[POST] userVO (age) = " + userVO.getAge());
//        return userVO.getName() + " " + userVO.getAge();
//    }

    // ============== DTO 이용 whit. axios ==============
    @GetMapping("/axios/res1")
    @ResponseBody
    public String axiosRes1(@RequestParam String name, @RequestParam int age) {
        return "이름 : " + name + " 나이 : " + age;
    } // o

    @GetMapping("/axios/res2")
    @ResponseBody
    public String axiosRes2(UserDTO userDTO) {
        return "이름 : " + userDTO.getName() + " 나이 : " + userDTO.getAge();
    } // o

    @PostMapping("/axios/res3")
    @ResponseBody
    // axios 로 값을 전달하게 될 경우, Post 로 값을 보내면 파라미터로 값이 들어오지 않는다
    // @RequestParam request true 가 기본
    // 값이 들어오지 않는데 request true 여서 에러
    public String axiosRes3(@RequestParam String name, @RequestParam int age) {
        return "이름 : " + name + " 나이 : " + age;
    } // x (error)

    @PostMapping("/axios/res4")
    @ResponseBody
    // Axios 로 Post 요청을 보낼 경우 본문에 데이터가 들어가기에 @ModelAttribute 가 확인 불가 -> null
    public String axiosRes4( UserDTO userDTO) {
        return "이름 : " + userDTO.getName() + " 나이 : " + userDTO.getAge();
    } // o (null 0)

    @PostMapping("/axios/res5")
    @ResponseBody
    public String axiosRes5(@RequestBody UserDTO userDTO) {
        return "이름 : " + userDTO.getName() + " 나이 : " + userDTO.getAge();
    } // o

//    실습. Axios - DTO
//    @GetMapping("/axios/res1")
//    @ResponseBody
//    public String axiosRes1(@RequestParam String name, @RequestParam String age) {
//        System.out.println("[GET] axios (name) = " + name);
//        System.out.println("[GET] axios (age) = " + age);
//        return "이름: " + name + ", 나이: " + age;
//    }
//    @GetMapping("/axios/res2")
//    @ResponseBody
//    public String axiosRes2(UserDTO userDTO) {
//        System.out.println("[GET] axios and dto (name) = " + userDTO.getName());
//        System.out.println("[GET] axios and dto (age) = " + userDTO.getAge());
//        return "이름: " + userDTO.getName() + ", 나이: " + userDTO.getAge();
//    }
//    @PostMapping("/axios/res3")
//    @ResponseBody
//    public String axiosRes3(@RequestParam String name, @RequestParam String age) {
//        System.out.println("[POST] axios (name) = " + name);
//        System.out.println("[POST] axios (age) = " + age);
//        return "이름: " + name + ", 나이: " + age;
//    }
//    @PostMapping("/axios/res4")
//    @ResponseBody
//    public String axiosRes4(UserDTO userDTO) {
//        System.out.println("[POST] axios and dto (name) = " + userDTO.getName());
//        System.out.println("[POST] axios and dto (age) = " + userDTO.getAge());
//        return "이름:" + userDTO.getName() + ", 나이: " + userDTO.getAge();
//    }
//    @PostMapping("/axios/res5")
//    @ResponseBody
//    public String axiosRes5(@RequestBody UserDTO userDTO) {
//        System.out.println("[POST] axios and dto (name) = " + userDTO.getName());
//        System.out.println("[POST] axios and dto (age) = " + userDTO.getAge());
//        return "이름:" + userDTO.getName() + ", 나이: " + userDTO.getAge();
//    }

    // ============ VO 이용 ============
    @GetMapping("/axios/vo/res1")
    @ResponseBody
    public String axiosVoRes1(@RequestParam String name, @RequestParam int age) {
        return "이름: " + name + ", 나이: " + age;
    } // o

    @GetMapping("/axios/vo/res2")
    @ResponseBody
    public String axiosVoRes2(UserVO userVO) {
        return "이름: " + userVO.getName() + ", 나이: " + userVO.getAge();
    } // o (null, 0)

    @PostMapping("/axios/vo/res3")
    @ResponseBody
    public String axiosVoRes3(@RequestParam String name, @RequestParam String age) {
        return "이름: " + name + ", 나이: " + age;
    } // error

    @PostMapping("/axios/vo/res4")
    @ResponseBody
    public String axiosVoRes4(UserVO userVO) {
        return "이름:" + userVO.getName() + ", 나이: " + userVO.getAge();
    } // o

    @PostMapping("/axios/vo/res5")
    @ResponseBody
    // @RequestBody 로 값을 전달할 때, setter 없이도 값이 들어간다.
    // setter 함수 실행이 아니라 각각의 필드 (변수)에 직접저그로 값을 주입하면서 매핑
    public String axiosVoRes5(@RequestBody UserVO userVO) {
        return "이름:" + userVO.getName() + ", 나이: " + userVO.getAge();
    } // o
}
