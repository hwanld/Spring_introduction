package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } //MemberService 객체의 repository를 외부에서 받아서 저장해준다 ->depencency injection

    /*
     회원 가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원X

        //Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(member); //ctrl+alt+shift+t : method extract
        //result.ifPresent(m-> {
        //  throw new IllegalAccessException("이미 존재하는 회원입니다. "));
        //Optional을 사용해서 객체를 꺼내오면 Optional의 method들을 사용할 수 있어서 대부분 Optional로 가져온다!
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m-> {
                    throw new IllegalStateException("이미 존재하는 회원입니다. ");
                });
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}