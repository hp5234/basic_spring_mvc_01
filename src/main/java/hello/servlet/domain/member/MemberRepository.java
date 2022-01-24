package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음
 * 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // 싱글톤으로 생성
    private static final MemberRepository instance = new MemberRepository();

    // 싱글톤으로 만들기 위해 private 를 통해 생성자를 차단한다.
    private MemberRepository () {

    }

    public static MemberRepository getInstance(){
        return instance;
    }

    // 저장
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member); // HashMap 에 저장
        return member;
    }

    // 단일 조회
    public Member findById(Long id) {
        return store.get(id); // HashMap 에서 id 로 조회
    }

    // 전체 조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // HashMap 에서 값들을 꺼내서 ArrayList 에 넘겨준다.
        // ArrayList 의 값들을 조작해도 store 의 값들을 변경하지 않기 위함
        // 주의 : Member 를 가져와서 변경하면 변경된다, 위같은 구성은 store 를 보호하기위함 Member를 보호하기위한 구성이 아님
    }

    // store 비우기
    // 테스트 상황에서만 사용하는 용도
    public void clearStore() {
        store.clear();
    }
}
