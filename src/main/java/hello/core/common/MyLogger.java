package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

// proxy를 사용해 이 클래스를 사용하는 request 스코프를 가진 빈으로 controller 역을 하는 클래스에서 가짜 요청을 담아 주는 proxy객체를 만들어준다.
// => application을 실행할 때 spring container에 해당하는 클래스를 상속받는 가짜 proxy 객체를 생성해 빈으로 등록된다.
// => DI도 이 가짜 객체로 주입됨
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("["+uuid+"]" +"["+requestURL+"]"+message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("["+ uuid +"] request scope bean created" + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("["+ uuid +"] request scope bean closed" + this);
    }
}
