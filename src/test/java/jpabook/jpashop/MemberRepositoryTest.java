package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional
  @DisplayName("멤버테스트")
  void testMember(){
    //given
    Member member = new Member();
    member.setName("기모찌");

    //when
    //Long id = memberRepository.save(member);
    //Member findMember = memberRepository.find(id);

    //then
    //Assertions.assertThat(findMember).isSameAs(member);
  }


}