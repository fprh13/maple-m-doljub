package maple.doljub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maple.doljub.common.exception.CustomException;
import maple.doljub.common.validation.ValidationSequence;
import maple.doljub.dto.LoginDto;
import maple.doljub.dto.MemberResDto;
import maple.doljub.dto.MemberSignUpReqDto;
import maple.doljub.dto.MemberUpdateReqDto;
import maple.doljub.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupDto", new MemberSignUpReqDto());
        return "signup";
    }

    @PostMapping("/signup/process")
    public String signupProcess(@Validated(ValidationSequence.class) @ModelAttribute("signupDto") MemberSignUpReqDto memberSignUpReqDto,
                                BindingResult result) {
        /*validation*/
        if (result.hasErrors()) {
            return "/signup";
        }
        /*아이디 중복 확인*/
        boolean isMember = memberService.existsByLoginId(memberSignUpReqDto.getLoginId());
        if (isMember) {
            result.rejectValue("loginId", "duplicate", "이미 사용 중인 아이디입니다.");
            return "/signup";
        }
        /*회원가입 진행*/
        memberService.join(memberSignUpReqDto);
        return "redirect:/login";
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }


    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    /**
     * 회원 정보
     */
    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("member", memberService.find(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "mypage";
    }

    /**
     * 회원 정보 수정
     */
    @GetMapping("/mypage/update")
    public String update(Model model) {
        String email = memberService.find(SecurityContextHolder.getContext().getAuthentication().getName()).getEmail();
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setEmail(email);
        model.addAttribute("updateDto", memberUpdateReqDto);
        return "memberUpdateForm";
    }

    @PostMapping("/member/update/process")
    public String updateProcess(@Validated(ValidationSequence.class) @ModelAttribute("updateDto") MemberUpdateReqDto memberUpdateReqDto,
                         BindingResult result, Model model) {
        /*validation*/
        if (result.hasErrors()) {
            return "memberUpdateForm";
        }
        /* 업데이트 진행*/
        try {
            memberService.update(memberUpdateReqDto);
            return "redirect:/mypage";
        } catch (CustomException e) {
            model.addAttribute("error", "비밀번호 정보를 다시 확인해주세요");
            return "memberUpdateForm";
        }
    }

    /**
     * 회원 탈퇴
     */
//    @DeleteMapping("/member/delete")
//    public String delete() {
//    }

}
