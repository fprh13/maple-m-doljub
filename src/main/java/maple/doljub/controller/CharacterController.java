package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.common.exception.CustomException;
import maple.doljub.domain.Character;
import maple.doljub.domain.Member;
import maple.doljub.dto.CharacterInfoResDto;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.service.CharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/register")
    public String characterRegistrationPage(Model model) {
        model.addAttribute("characterDto", new CharacterRegisterReqDto());
        return "characterRegisterForm";
    }

    /**
     * 캐릭터 등록
     */
    @PostMapping("/character/register/process")
    public String characterRegistrationProcess(CharacterRegisterReqDto characterRegisterReqDto, RedirectAttributes redirectAttributes) {
        try {
            characterService.join(characterRegisterReqDto);
            return "redirect:/character";
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("characterError", "캐릭터를 찾을 수 없습니다.");
            return "redirect:/character/register";
        }
    }

    /**
     * 내 캐릭터 모두 보기
     */
    @GetMapping("/character")
    public String characterList(Model model) {
        Member member = characterService.findMyCharacters();
        if (member != null) {
            List<Character> characters = member.getCharacters();
            if (characters != null) {
                model.addAttribute("characters", characters);
            } else {
                model.addAttribute("characters", Collections.emptyList()); // Empty list if characters are null
            }
        } else {
            model.addAttribute("characters", Collections.emptyList()); // Empty list if user is null
        }
        return "character";
    }

    /**
     * 다른 사람 캐릭터 상세
     */
    @GetMapping("/character/info/{name}")
    public String characterInfo(Model model, @PathVariable("name") String name) {
        model.addAttribute("character", characterService.info(name));
        return "characterInfo";
    }

    @GetMapping("/character/search")
    public String characterInfoOther(Model model, RedirectAttributes redirectAttributes , @RequestParam("name") String name, @RequestParam("world") String world) {
        try {
            CharacterInfoResDto characterInfoResDto = characterService.search(name, world);
            model.addAttribute("character", characterInfoResDto);
            return "characterInfo";
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("error", "캐릭터를 찾을 수 없습니다.");
            return "redirect:/";
        }
    }
}
