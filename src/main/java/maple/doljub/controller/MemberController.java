package maple.doljub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maple.doljub.dto.LoginDto;
import maple.doljub.dto.MemberSignUpReqDto;
import maple.doljub.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String signupProcess(MemberSignUpReqDto memberSignUpReqDto) {
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
