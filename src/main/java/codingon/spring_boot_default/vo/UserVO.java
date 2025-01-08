//package codingon.spring_boot_default.vo;
//
//import lombok.Getter;
//
//@Getter
//public class UserVO {
//    private String name;
//    private int age;
//
//    // 동등성 구현을 위한 코드
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if ( o == null || getClass() !=o.getClass()) return false;
//
//        UserVO userVO = (UserVO) o;
//
//        // 모든 필드 비교
//        return Object.equals(name, userVO.getName()) && (age == userVO.age);
//    }
//
//    @Override
//    public int hashCode() {
//        // 필드를 이용한 해시 코드 생성
//        return Object.hash(name, age);
//    }
//}

package codingon.spring_boot_default.vo;

import lombok.Getter;

@Getter
public class UserVO {
    private String name;
    private int age;

    // 동등성 구현을 위한 코드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 동일 객체일 경우 true 반환
        if (o == null || getClass() != o.getClass()) return false;  // null 검사와 타입 검사

        UserVO userVO = (UserVO) o;  // UserVO로 캐스팅

        // 필드 비교: name을 String.equals()로 비교하고, age는 값 비교
        return name != null && name.equals(userVO.name) && age == userVO.age;
    }

    @Override
    public int hashCode() {
        // 필드를 이용한 해시 코드 생성: name이 null일 경우를 고려해 name이 null이면 기본값 사용
        return 31 * (name != null ? name.hashCode() : 0) + age;
    }
}
