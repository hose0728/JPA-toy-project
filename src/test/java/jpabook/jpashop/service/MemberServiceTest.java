package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberRepositoryOld memberRepositoryOld;

  @Autowired
  MemberService memberService;

  @Test
  public void 회원가입() throws Exception {

    //Given

    Member member = new Member();
    member.setName("이렐리아");


    //When
    Long saveId = memberService.join(member);


    //Then
    assertThat(member).isEqualTo(memberRepositoryOld.findOne(saveId));


  }

  @Test()
  public void 중복_예외() throws Exception {

    //Given
    Member member = new Member();
    member.setName("다리우스");

    Member member2 = new Member();
    member2.setName("다리우스");

    //When
    memberService.join(member);
    //memberService.join(member2);
    assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    //Then



  }

}