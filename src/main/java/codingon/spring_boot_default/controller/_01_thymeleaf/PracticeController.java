package codingon.spring_boot_default.controller._01_thymeleaf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PracticeController {
    @GetMapping("people")
    public String getPeople(Model model) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("홍길동", 10));
        personList.add(new Person("김민지", 10));
        personList.add(new Person("성춘향", 10));
        model.addAttribute("personList", personList);

        return "_01_thymeleaf/practice";
    }
}

@Getter
@Setter
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}
