package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;


// Application의 동작에 필요한 구현 객체를 생성한다.

public class AppConfig {

    public MemberService memberService(){
        // 생성자 주입
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        // 생성자 주입
        return new OrderServiceImpl( new FixDiscountPolicy(),new MemoryMemberRepository());
    }
}
