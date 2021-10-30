//ctrl+shift+T 를 사용해서 자동으로 Test를 생성할 수 있다.
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //TestCase에서는 가장 편한 방법을 선호하기 때문에 그냥 @Autowired로 받아온다. Constructor를 통해서 injection을 해줄 필요가 없다.

    //@BeforeEach를 써서 객체를 생성해서 주입해줬지만 이제는 SpringBoot로부터 받아오면 되기 때문에 필요가 없다
    //@AfterEach를 써서 한번 테스트가 끝날 때 마다 DB를 초기화 하였으나 @Transactional때문에 필요가 없다

    @Test
    @Commit
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
}