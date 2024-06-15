package maple.doljub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maple.doljub.common.validation.ValidationSequence;
import maple.doljub.dto.LoginDto;
import maple.doljub.dto.MemberSignUpReqDto;
import maple.doljub.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
     * 회원 정보 수정
     */

}
