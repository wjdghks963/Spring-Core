# 스프링과 객체 지향

핵심 기술 : 스프링 DI 컨테이너, AOP, 이벤트 등..
웹 기술 : 스프링 MVC, 스프링 WebFlux
데이터 접근 기술: 트랜잭션, JDBC, ORM 지원, XML 지원 기술 통합: 캐시, 이메일, 원격접근, 스케줄링 테스트: 스프링 기반 테스트 지원 
스프링 부트는 스프링을 편리하게 사용가능하게 해주는 기술이다.

## 스프링 부트

스프링을 편리하게 사용할 수 있도록 지원해준다.

단독으로 실행할 수 있는 스프링 애플리케이션을 쉽게 생성
 Tomcat 같은 웹 서버를 내장해서 별도의 웹 서버를 설치 및 설정하지 않아도 됨

손쉬운 빌드 구성을 위한 starter 종속성 제공

외부 라이브러리 자동 구성
 메트릭, 상태 확인, 외부 구성 같은 프로덕션 준비 기능 제공 관례에 의한 간결한 설정

결국 스프링은 자바의 가장 큰 특징인 객체 지향의 장점들을 더 잘 살려낼 수 있는 프레임워크다.


## 객체지향

객체 지향의 특징으로는 추상화, 캡슐화, 상속, 다형성이 있다.

객체 지향 프로그래밍은

1. 프로그램을 여러 개의 독립된 단위, 즉 **객체들의 모임**으로 파악하고자 하는 것이다. **각각의 객체는 메시지 를 주고받고, 데이터를 처리한다.**

2. 프로그램을 **유연하고 변경이 용이**하게 만들기 때문에 대규모 소프트웨어 개발에 많이 사용된다.


### 다형성

다형성은 역할과 구현으로 구분된다.

다형성을 이용해 역할과 구현으로 구분하면 단순하고 유연해 변경이 편리해진다.

장점
1. 클라이언트는 대상의 역할만 알면된다.
2. 클라이언트는 구현 대상의 내부 구조를 몰라도 된다.
3. 클라이언트는 구현 대상의 내부 구조가 변경되어도 영향 받지 않는다.
4. 클라이언트는 구현 대상 자체를 변경해도 영향 받지 않는다.

자바는 Interface를 이용해 역할을 만들어서 객체를 생성 가능하다.
interface의 수정으로 역할은 유연하게 바꿀 수 있으며 구현한 객체도 변경이 바로 적용된다.
따라서 역할과 구현이 명확하게 분리되어 있다 할 수 있다.

스프링은 이 다형성을 극대화해서 사용할 수 있게 도와준다.


### SOLID

객체 지향에서 클린 코드를 위한 원칙 5가지

SRP : 단일 책임 원칙

하나의 클래스는 하나의 책임만 가져야한다. 만약 변경이 일어날 경우 파급력이 작아야한다.

**Spring에서 어떻게 구현하나**
- 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당한다.
- 클라이언트 객체는 실행하는 책임만 담당한다.

OCP : 개방/폐쇄 원칙

요소는 확장에는 열려있지만 변경에는 닫혀있어야한다.
interface는 변경해도 클라이언트 코드는 변경되면 안된다.
만약 이런 경우가 생긴 경우 객체를 생성, 연관관계를 맺어주는 별도의 조립, 설정자가 필요하다.

**Spring에서 어떻게 구현하나**
- Application을 사용 영역과 구성 영역을 분리함
- AppConfig가 의존관계를 변경가능하고 클라이언트 코드에 주입하기 때문에 코드를 바꾸지 않아도 된다.

LSP : 리스코프 치환 원칙

객체는 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야한다.
인터페이스 규약을 지켜야한다.

ISP : 인터페이스 분리 원칙

특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
인터페이스가 명확해지고 대체 가능성이 높아진다.

DIP : 의존관계 역전 원칙

구체화에 의존하지 말고 추상화에 의존해야한다.
클라이언트 코드는 자신의 인터페이스만 바라봐야한다. 다른 구현체에 의존해 코드가 작동하면 안된다.

**Spring에서 어떻게 구현하나**
- 클라이언트 코드를 추상화 인터페이스만 의존하게 만든다.
- AppConfig가 클라이언트에서 사용하는 객체를 생성해 의존관계를 주입해준다.

## 스프링이 어떻게 객체지향을 지원해줄까

DI, DI Container를 이용해 클린 코드 원칙에서 지키기 어려웠던 OCP, DIP를 가능하게 지원해준다.

<br>
<hr>

# AppConfig

클래스 안에서 사용하는 구현체를 없애고 추상화(interface)된 객체만을 남기고 생성자를 만들어 여기에서 관리를 할 수 있게 한다.

=> 관심사를 완벽하게 분리해 코드에서 DIP 원칙을 준수할 수 있게 만들어준다.

Application의 실제 동작에 필요한 **구현객체**를 생성한다.
생성한 객체 인스턴스의 참조를 **생성자를 통해 주입**해준다.

1. DIP 원칙을 준수하게 된 추상화된 개념만을 갖게되는 클래스 입장에서는 생성자를 통해 어떤 구현 객체가 들어올지 알 수 없다.
2. 생성자를 통해 어떤 구현 객체를 주입할지는 오직 외부(이 파일)에서 결정된다.
3. **의존 관계에 대한 고민을 외부에 맡기고 실행에만 집중할 수 있다.** => 관심사의 분리가 완벽하게 이루어짐

사용하기 위해서는 Application 레벨에서 Appconfig를 생성해야한다.

```java
AppConfig appConfig = new AppConfig();
```

