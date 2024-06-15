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
import org.springframework.validation.BindingResult;
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
    public String characterRegistrationProcess(@ModelAttribute("characterDto") CharacterRegisterReqDto characterRegisterReqDto, Model model) {
        try {
            characterService.join(characterRegisterReqDto);
            return "redirect:/character";
        } catch (CustomException e) {
            model.addAttribute("error", "캐릭터를 찾을 수 없습니다.");
            return "characterRegisterForm";
        }
    }

    /**
     * 내 캐릭터 모두 보기
     */
    @GetMapping("/character")
    public String characterList(Model model) {
        List<Character> characters = findCharactersIfExists();
        model.addAttribute("characters", characters);
        return "character";
    }

    /*캐릭터의 유무에 맞게 반환*/
    private List<Character> findCharactersIfExists() {
        Member member = characterService.findMyCharacters();
        return (member != null) ? member.getCharacters() : Collections.emptyList();
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
    public String characterInfoOther(Model model, @RequestParam("name") String name, @RequestParam("world") String world) {
        try {
            CharacterInfoResDto characterInfoResDto = characterService.search(name, world);
            model.addAttribute("character", characterInfoResDto);
            return "characterInfo";
        } catch (CustomException e) {
            model.addAttribute("characterError", "캐릭터를 찾을 수 없습니다.");
            return "main";
        }
    }
}
