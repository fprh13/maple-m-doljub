package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.dto.CharacterDto;
import maple.doljub.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/register")
    public String characterRegistrationPage(Model model) {
        model.addAttribute("characterDto", new CharacterDto());
        return "characterRegisterForm";
    }

    @PostMapping("/character/register/process")
    public String characterRegistrationProcess(CharacterDto characterDto) {
        characterService.join(characterDto);
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
