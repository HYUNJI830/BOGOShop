package cosmetics.BOGOShop.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {
    public static enum UserType{
        USER, ADMIN
    }

    UserType type();

    //@Retention
    //어노테이션의 라이프사이클을 정의
    //RetentionPolicy.SOURCE : 소스코드에만 존재, 컴파일러가 컴파일할 때 제거 / 코드 분석 도구나 IDE 플러그인 사용
    //RetentionPolicy.CLASS : 컴파일된 클래스
    //RetentionPolicy.RUNTIME : 런타임 동안 유지, 런타임에 동적으로 처리할때

    //@Target
    //어노테이션 적용할 수 있는 요소를 지정 // 사용위치 제한
    //ElementType.TYPE: 클래스, 인터페이스(애너테이션 포함), 열거형에 적용
    //ElementType.TYPE_PARAMETER: 제네릭 타입 매개변수에 적용
}
