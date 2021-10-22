package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepositoryOld memberRepositoryOld;

  /**
   * @param member
   * @return member의 ID값값
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicateMember(member); // 중복 회원 검증
    return memberRepositoryOld.save(member);

  }

  private void validateDuplicateMember(Member member) {
    //Exception
    List<Member> result = memberRepositoryOld.findByName(member.getName());
    if (!result.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  //회원 전체 조회
  public List<Member> findMembers() {
    return memberRepositoryOld.findAll();
  }

  public Member findOne(Long id) {
    return memberRepositoryOld.findOne(id);
  }

  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepositoryOld.findOne(id);
    member.setName(name);
  }
}
