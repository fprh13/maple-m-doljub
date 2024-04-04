package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.service.CharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/register")
    public String characterRegistrationPage(Model model) {
        model.addAttribute("characterDto", new CharacterRegisterReqDto());
        return "characterRegisterForm";
    }

    @PostMapping("/character/register/process")
    public String characterRegistrationProcess(CharacterRegisterReqDto characterRegisterReqDto) {
        characterService.join(characterRegisterReqDto);
        return "redirect:/";
    }

    /**
     * 내 캐릭터 모두 보기
     */

    /**
     * 다른 사람 캐릭터 상세
     */

    /**
     *
     */

}
