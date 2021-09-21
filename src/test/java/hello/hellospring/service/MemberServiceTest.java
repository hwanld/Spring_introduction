//ctrl+shift+T 를 사용해서 자동으로 Test를 생성할 수 있다.
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    /*
    아래 코드처럼 repository를 새로 생성하게 되면, 위의 memberService 내의 reposiroty와 아래의 repository는 다른 repository가 된다.
    따라서 memberService를 각 메소드가 실행되기 전에 객체를 만들어서 집어넣어준다.
    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); ->depencency injection
    */


    /*
    여러가지 test method에 의해서 중복되는 값이 저장이 될 수 있기 때문에 memberRepository를 만들고
    AfterEach를 사용해서 매 번 초기화를 해준다.
    */
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() { //Testcase의 method들은 한글로 해도 무방하다
        //given when then 주석을 사용해서 testcase를 짜면 편하다 (주석을 깔고 점점 변형할 수 있도록 한다!)

        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    //Testcase의 경우는 예외flow가 훨씬 더 중요하다
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. ");

/*      //try catch문법을 사용하는 경우_권장되는 사용법은 아님
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. ");
        }
*/


        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}