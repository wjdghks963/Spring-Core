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

<hr>

# IOC 제어의 역전

AppCofig를 사용해서 개발한다면 구현 객체는 자신의 로직을 실행하는 역할만 담당하게끔 되었다.
=> 구현 객체를 생성하고 생성자를 통해 의존관계를 주입해줌 개발자는 그저 설정을 하고 추상적인 객체를 가지고 controll하는 클래스를 제작하면 된다.

프로그램에 대한 제어 흐름에 대한 권한이 이제 모두 AppConfig가 가지게 된것이다.
모든 객체에 대해 생성하고 실행하는 것은 이제 AppConfig가 권한을 가지고 프로그램을 실행 시키는 것이다.

이렇게 프로그램의 제어 흐름을 직접 제어하지 않고 외부에서 관리하는 것을 제어의 역전이라 한다.

=> 프레임 워크가 이와 같다.

<hr>

# DI 의존관계 주입

인텔리 제이를 사용한다면 현재 어떤 관계가 맺어져 있는지 확인할수있는 툴을 제공해준다. = 다이어그램으로 표현

Spring에 의해 Impl은 인터페이스에 의존하게 만들었다.
실제로 어떤 구현 객체가 사용될지는 모른다.

의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 한다.

**1. 정적인 객체 의존 관계**
- 클래스가 사용하는 import 코드만 보고 의존관계를 쉽게 판단할 수 있다.
- 정적인 의존관계는 프로그램을 실행하지 않아도 분석이 가능하다.

**2. 동적인 객체 의존 관계**
- 프로그램 실행시(**런타임에서**) 실제 생성된 객체의 참조가 연결된 의존관계

### 결론

**의존관계 주입을 사용하면** 

클라이언트 코드를 변경하지 않고 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.

정적인 클래스 의존관계를 변경하지 않고 동적인 인스턴스 관계를 쉽게 변경할 수 있다.

**Process**
1. 런타임에 외부에서 실제 구현 객체를 생성하고 => AppConfig setting 후 application level에서 인스턴스화
2. 클라이언트에 전달해 클라이언트와 서버의 실제 의존관계가 연결된다. => 이제 인스턴스를 이용한 의존관계가 생성되며 사용하는 것이 아니라 추상적으로 작성된 클래스 사용이 가능하다.

<br>

AppConfig와 같이 객체를 생성해주고 관리하며 의존관계를 연결해 주는 것을 IOC or DI Container라고 한다.

<br>
<br>

# Spring  

Spring Annotation을 이용해서 해당하는 클래스가 어떤 역할을 하는지 알게 해주고 그것을 역할에 맞게 사용한다.

**Process**

1. Spring Container 생성
2. Spring Bean 등록
3. Spring Bean 의존관계 설정 준비, 완료 => 동적인 의존관계를 설정해준다.


## Bean

Bean을 등록할때 `@Bean`을 사용하며 이 Bean의 key(name)는 기본값은 Appconfig에서 메서드의 이름으로 등록된다.
이 이름은 같은 값이 존재하면 절대 안된다. 

name 설정 방법 : `@Bean(name="name")`

스프링 컨테이너에 올려져 있는 Bean을 찾기 위해서는 `applicationContext.getBean()` 메서드를 사용한다.

### 상속

스프링 빈에서 부모 타입으로 조회하면 자식 타입도 함께 조회한다.
따라서 모든 자바 객체의 최고 부모인 `Object`타입으로 조회하면 모든 스프링 빈을 조회하게된다.

### BeanDefinition

BeanDefinition 인터페이스를 사용해 스프링에서 Bean은 역할과 구현을 개념적으로 나눈다.

`AnnotationConfigApplicationContext`에서 인자로 받은 AppConfig에 있는 Bean들의 정보를 컨테이너에 올려놓는다.


## Spring Container

`ApplicationContext`를 스프링 컨테이너라고 한다.
스프링 컨테이너는 `@Configuration`이 붙은 AppConfig를 설정 정보로 사용한다.

AppConfig에 있는 Bean으로 등록된 메서드를 모두 호출해 반환된 객체를 스프링 컨테이너에 등록한다.
이제 스프링 컨테이너에 들어간 객체를 스프링 빈이라 한다.

컨테이너 생성

안에 인자는 사용하고자 하는 Bean들이 등록되어 있는 AppCofig.class를 넣어줘야한다.

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
```


### ApplicationContext

스프링 컨테이너의 최상위 인터페이스 Bean Factory로부터 기능을 상속받아 제공한다.

Bean Factory와 차이점
1. 메지시 소스를 활용한 국제화 기능
2. 환경변수
3. 애플리케이션 이벤트
4. 편리한 리소스 조회


# Singleton Pattern

스프링이 아닌 순수한 DI Container는 요청이 있을 때마다 객체들을 새로 생성한다.

## 개념

**클래스의 인스턴스가 무조건 1개만 생성되는 것을 보장하는 패턴**

클래스 내에서 로직을 만들어 객체가 2개 이상 생성되는 것을 막아야한다.

**구현**
1. static 영역에 객체 instance를 올림
2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

**주의점**
싱글톤은 같은 객체를 참조하기 때문에 만약 클래스 내에 공유 field가 존재하면 안된다.
=> 스레드가 무수히 많은 요청 중에 필드를 바꾼다면 결과값이 예상한대로 나오지 않는다.

ex) SR(singleton 객체)을 A가 참조해서 공유 필드를 1로 바꿈 B가 참조해서 공유 필드를 2로 바꿈 나중에 A를 이용해서 1로 바꾼 값을 사용하기 위해서 접근했더니 2로 나옴

**따라서 싱글톤(스프링빈)을 사용할 때는 무상태로 설계를 해야한다.**

<br>

## Singleton Container

스프링 컨테이너는 객체들을 모두 싱글톤으로 생성해서 관리한다.

만약 요청이 있을 때마다 계속 객체들을 새로 생성한다면 메모리 낭비가 심해지기 때문에 효율을 위해 스프링은 싱글톤 패턴을 사용한다.
하지만 싱글톤 방식만 지원하는 것이 아니라 요청할 때마다 새로운 객체를 생성해 주는 것도 가능하다. 

이제 스프링 컨테이너 안에서 객체는 요청마다 새로 생성되는 것이 아니라 싱글톤으로 관리 되는 Bean을 참조하게 되며 효율적으로 재사용이 가능하다.

### 그러면 어떻게 알고 싱글톤으로 재사용하냐

일반 자바 코드로만으로 새 객체를 만드는 것을 캐치하고 만드는 것을 저지하고 싱글톤으로 만들어진 객체를 다시 재사용하는 것은 매우 어렵다.

따라서 스프링 내부에서 바이트 코드(.class)를 제어하는 기술을 사용한다.

=> 만약 이 Bean이 Spring Container안에 등록되어 있다면 만들지 않고 container안에서 찾아서 반환하고 등록되어 있지 않다면 Bean을 생성해서 container에 등록한다.

이것은 Annotation으로 Appconfing가 `@Configuration`이 붙었을때 작동하며 만약 Appconfig에 붙어있지 않는다면 싱글톤이 깨진다.


<br>
<hr>

# Component Scan

기존에 AppConfig에서 설정했던 Bean들은 수가 많아지면 관리가 어렵다.

따라서 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.
**컴포넌트 스캔은 `@Component`가 붙어있는 클래스들을 스캔한 후 빈으로 등록한다.**

컴포넌트 스캔을 사용하기 위해선 `@ComponentScan`을 설정 정보에 붙여줘야한다.

filter를 통해 해당하는 어노테이션이 붙은 클래스를 스캔에서 제외가 가능하다. => 빈으로 자동 등록 안함
 
<br>

**이름**

`@Component`로 자동적으로 등록되는 빈의 이름 기본값은 소문자로 이루어진 클래스 이름이다.

만약 빈의 이름을 바꾸고 싶다면 `@Component("name")`을 사용해 변경이 가능하다.

<br>

**의존관계 자동 주입**

클래스가 의존하는 생성자에 `@Autowired`를 사용하면 스프링 컨테이너가 자동으로 스프링 빈을 찾아서 주입해 준다.

기존 조회 전략은 타입이 같은 빈을 찾아서 주입한다. => `ac.getBean(className)`

<br>

## 기본 대상

스캔하는 대상은 `@Component`뿐만 아니라 Component를 기반으로 한 Annotation도 스캔한다.

- `@Controller` : MVC controller
- `@Service` : 비지니스 로직
- `@Repository` : 데이터 접근 계층
- `@Configuration` : 설정 정보

<br>

## Filter 

컴포넌트 스캔에서 제외, 추가할 대상을 지정할 수 있다.

ComponentScan 내에서 include, exclude를 통해 스프링 컨테이너에 올라갈 빈을 등록하도록 혹은 등록하지 못하도록 만들 수 있다.


Custom Annotation 제작한 후 filter하기

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {}

@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class))
class ComponentFilterAppConfig{
}
```

<br>

## 중복 등록과 충돌

컴포넌트 스캔할때 중복 등록에 의한 충돌이 날 가능성이 있다.

**1. 자동 vs 자동**\
스프링의 어노테이션을 이용해 자동으로 컨테이너에 빈을 등록할 경우 중복으로 등록되어 충돌이 날 수 있다.
이 경우는 중복되는 빈의 이름 때문에 생기는 경우다. 

**2. 수동 vs 자동**\
수동적으로 컨테이너에 빈을 등록할 때 자동으로 빈을 등록할 때도 빈의 이름이 동일할 경우 충돌이 날 수 있다.

AppConfig 파일(@Configuration)에서 수동으로 빈을 등록할 때 이름이 자동으로 등록되는 경우다.
이때 우선권은 수동으로 등록한 이름이 가져간다. => 수동 빈이 자동 빈을 오버라이딩한다.

#### 조회 대상 빈이 2개 이상일 때

이게 무슨 소리냐 왜 싱글톤인 이름이 유니크한 빈이 조회가 두 개 이상이 될까\
1. 생성자에서 상위 인터페이스를 기준으로 인자를 받게 되고
2. 하지만 상위 인터페이스로 구현한 객체가 빈으로 등록된 객체가 2개 이상이다.
3. 이러면 구현체 중에서 어떤 객체를 가져와야 할지 모른다.

ex) 
```java
interface Car {
    void run()
    void setSpeed()
}

@Component
class Kia implements Car { }

@Component
class H implements Car{ }

@Component
class shop {
    private Car car;
    // KIA or H 중에서 어떤 빈을 가져와야하는지 모름 => 에러
    Shop(Car car){
        this.car = car;
    }
}
```



**`@Autowired` 필드명 매칭**\
1. 타입 매칭
2. 타입 매칭 결과가 2개 이상일 떄 필드명 파라미터명으로 빈 이름 매칭

```java
@Component
class Shop {
    private Car car;
    // 자동적으로 kia 빈을 가져온다.
    Shop(Car kia){
        this.car = kia;
    }
}
```

**`@Quilfier` -> `@Quilfier`끼리 매칭 -> 빈 이름 매칭**\
추가 구분자를 붙여주는 방법\
주입시 추가적인 방법을 제공하는 것이다. => 빈 이름을 변경하지 않는다.

```java
interface Car {
    void run()
    void setSpeed()
}

@Component
@Quilfier("kia")
class Kia implements Car { }

@Component
@Quilfier("h")
class H implements Car{ }

@Component
class Shop {
    private Car car;
    Shop(@Qualifier("kia") Car car){
        this.car = car;
    }
}
```


**`@Primary` 사용**\
우선 순위를 정하는 방법\

```java
@Component
@Primary
class Kia implements Car { }

@Component
class H implements Car{ }

@Component
class Shop {
    private Car car;
    // Kia가 우선권을 가진다.
    Shop(Car car){
        this.car = car;
    }
}
```


<br>
<br>
<hr>

# 의존 관계 주입

의존 관계를 주입할 때 4가지 방법이 존재한다.

요즘 트렌드에서 권장되는 방법은 생성자 주입이다.

## ⭐️ 생성자 주입
**생성자(constructor)로 관계를 주입 받는 방법**

**특징**
- 생성자 호출시 1번만 호출되는 것을 보장할 수 있다.
- 불변, 필수 의존관계에 사용한다.
- 만약 생성자가 1개만 존재한다면 `@Autowired`를 사용하지 않아도 자동적으로 적용된다.

```java
@Component
class A {
    private B b;
    // @Autowired 넣지 않아도 자동 적용
    public A(B b){
        this.b = b;
    }
}

class B{
    public B(){}
}
```

### 불변

대부분의 의존 관계 주입은 한번 일어나면 Application이 종료되기 전까지 변경할 일이 없다.
오히려 불변해야한다.

생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 

### 누락

스프링에 의해 나중에 다른 곳에서 사용할 일이 없지만 만약 순수 자바를 이용해 테스트를 한다면 생성자로 구현하지 않을 경우 의존성 주입이 되지 않는다.
이때 누락이 발생해 컴파일 오류를 발생시킬 수 있다.

하지만 생성자로 구현한다면 테스트할 객체를 순수 자바로 생성해서 테스트가 가능하다.

#### final

생성자 주입을 사용하면 필드에 final 키워드(이 변수는 수정할 수 없다는 의미)를 사용할 수 있다. 
=> 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.

```java
class A {
    private final B b;
    public A(B b){
        // 만약 이 줄이 없다면 컴파일 오류를 반환한다.
        this.b = b;
    }
}
```


## 수정자 주입
**수정자(setter)메서드를 이용해 관계를 주입 받는 방법**

**특징**
- 선택, 변경 가능성이 있는 의존 관계에 사용한다.
- [자바빈 프로퍼티 규약](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=rbamtori&logNo=220760147541)의 수정자 메서드를 사용한다.
- 수정자는 public으로 열어둬야한다.

```java
@Component
class A {
    private B b;
    
    @Autowired
    public void setB(B b){
        this.b = b;
    }
}

class B{
    public B(){}
}
```


## 필드 주입
**필드(filed)에 주입 받는 방법**

**특징**
- 외부에서 변경 불가능하다.
- DI 프레임워크가 없다면 사용 불가하다.
- 사용 ❌

## 일반 메서드 주입
**일반 메서드로 관계를 주입 받는 방법**

**특징**
- 한번에 여러 필드 주입 받을 수 있다.
- 일반적으로 사용 ❌


<br>

# 롬복

**[롬복이란?](https://dololak.tistory.com/783)**

반복되는 getter, setter, toString 등의 메서드 작성 코드를 줄여주는 자바의 코드 다이어트 라이브러리

여러가지 어노테이션을 제공하고 이를 기반으로 코드를 컴파일과정에서 생성해 주는 방식으로 동작하는 라이브러리
=> 자바 코드로는 어노테이션만 붙이면 되지만 빌드 과정에서 생성된 바이트 코드(.class)에서는 코드가 다 작성된 상태가 된다.

```java
@Getter
@Setter
public class HelloLombok {
    
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        // 어노테이션으로 안에 getter setter가 존재하지 않아도 만들어짐 
        helloLombok.setName("lomlom");
        
        String name = helloLombok.getName();
        System.out.println("name = " + name);
    }
}
```

`RequiredArgsConstructor` 를 사용하게 된다면 클래스 내에 있는 final 필드를 모아 생성자를 자동으로 만들어 주기 때문에 생성자 코드를 생략해도 된다.


<br>
<br>

<hr>

# Bean 생명주기

Bean = 객체 생성 -> 의존관계 주입

DB와 connect, disconnect 같은 행위를 할때 Spring Container가 생성될 때나 죽을때나 Bean은 생사를 같이 한다.  

빈의 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하지만 초기화는 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.

따라서 생성자에서 간단한 것이 아니라면 초기화를 하는 것은 좋지 않기 때문에 동작을 분리해서 코드를 작성하는 것이 좋다.


## InitializingBean, DisposableBean

Interface를 implement하면서 생명상태에 대해 메서드를 작동 시킬 수 있게된다.

**InitializingBean**\
생성될 때 초기화하는 메서드 사용 
```java
 @Override
public void afterPropertiesSet() throws Exception {
    // Bean이 생성된 후 초기화를 하는 곳    
}
```

**DisposableBean**\
소멸될 때 메서드 사용
```java
 @Override
public void destory(){
    // Bean이 죽을 때 사용됨
        }
```

사용하기 간단하고 생명 주기에 꼭 맞는 메서드 같아 보이지만 단점들이 존재한다.
1. 외부 라이브러리 불가
2. 오버 라이딩이기 때문에 이름 변경 불가


## Bean에서 사용

```java
  @Bean(initMethod = "초기화 메서드", destroyMethod = "죽을 때 메서드")
```

1. 이제 메서드 이름 마음대로 가능
2. 외부 라이브러리 사용 가능
3. destroyMehod 같은 경우 defualt 값으로 클래스 내에 shutdown, close가 있을 경우 땡겨서 데려와 사용한다.

## Annotaion 사용

최신 스프링에서 권장되는 방법은 어노테이션을 사용하는 방법이다.

생성 = @PostConstruct\
죽음 = @PreDestroy

매우 사용하기 쉽지만 외부 라이브러리는 적용하지 못한다는 단점이 있다.\
만약 외부 라이브러리를 사용하고 싶다면 위의 Bean에서 제공하는 기능을 사용하는 것이 좋다.


<br>
<br>

<hr>

# Bean의 스코프

### 싱글톤 스코프
빈의 기본적인 스코프는 싱글톤 스코프다.\
따라서 스프링 컨테이너가 생성되고 종료될때까지 유지된다.

### 프로토타입 스코프
스프링 컨테이너가 항상 새로운 인스턴스를 생성해 반환한다. => 생성, DI, 초기화까지만 관여한다.


### ObjectFactory, ObjectProvider
Spring에 의존적인 DL 서비스를 제공해주는 class

ObjectFactory : 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공\
ObjectProvider : ObjectFactory에 더해 편의 기능을 제공해준다.

### 웹 스코프

웹 환경에서만 동작하며 프로토 타입과 다르게 싱글톤 스코프와 같이 스프링이 해당 스코프의 종료시점까지 관리하기 때문에 종료 메서드를 호출한다.

**종류**

`request` : HTTP 요청 하나가 처리될때까지 유지되는 스코프 각 요청마다 별도의 인스턴스가 생성되고 관리된다.\
`session` : HTTP session과 동일한 생명주기\
`application`  : 서블릿 컨텍스트( ServletContext )와 동일한 생명주기\
`websocket` : 웹 소켓과 동일한 생명주기

