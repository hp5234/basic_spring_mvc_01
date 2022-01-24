package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberRepositoryTest {

    // MemberRepository memberRepository = new MemberRepository()
    // 싱글톤으로 사용하기위해 getInstance() 사용 -> 생성자는 private 로 차단됨
    // 스프링을 사용하면 싱글톤을 보장하므로 해당 방식을 사용할 필요 없음
    MemberRepository memberRepository = MemberRepository.getInstance();

    // 각각의 테스트가 끝나고 초기화 실행
    // 테스트의 순서를 보장하지 않기때문에 오류 방지용으로 실시
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    // 테스트 시작
    // 저장 테스트
    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(savedMember); // 저장할 Member 와 저장된 member 가 같은지 확인
    }

    // 전체 조회 테스트
    @Test
    void findAll() {

        // given
        Member member1 = new Member("member1", 22);
        Member member2 = new Member("member2", 33);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findAll();

        // then
        // 저장된 데이터 갯수 확인
        Assertions.assertThat(result.size()).isEqualTo(2);
        // 저장할 데이터( Member )가 조회결과 ( result )에 포함되어 있는가
        Assertions.assertThat(result).contains(member1, member2);
    }

}
