package jpabook.jpashop.controller;

import javax.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.form.MemberForm;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @GetMapping(value = "/new")
  public String createForm(Model model){
      model.addAttribute("memberForm",new MemberForm());
      return "members/createMemberForm";
  }

  @PostMapping(value = "/new")
  public String create(@Valid MemberForm form, BindingResult result){
    if(result.hasErrors()){
      return "members/createMemberForm";
    }
    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);
    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping
  public String list(Model model){
    model.addAttribute("members", memberService.findMembers());
    return "/members/memberList";
  }

}
