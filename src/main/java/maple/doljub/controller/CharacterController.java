package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.common.exception.CustomException;
import maple.doljub.domain.Character;
import maple.doljub.domain.Member;
import maple.doljub.dto.CharacterDeleteReqDto;
import maple.doljub.dto.CharacterInfoResDto;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.service.CharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    /**
     * 캐릭터 등록
     */
    @GetMapping("/character/register")
    public String characterRegistrationPage(Model model) {
        model.addAttribute("characterDto", new CharacterRegisterReqDto());
        return "characterRegisterForm";
    }

    /**
     * Create : 캐릭터 등록
     */
    @PostMapping("/character/register/process")
    public String characterRegistrationProcess(@ModelAttribute("characterDto") CharacterRegisterReqDto characterRegisterReqDto, Model model) {
        // 이미 사용 중 인 경우 -> 추후 보조식별자를 아이템으로 두어 구분 예정
        if (characterService.existsCharacter(characterRegisterReqDto)) {
            model.addAttribute("error", "이미 사용 중 인 캐릭터 입니다.");
            return "characterRegisterForm";
        }
        try {
            characterService.join(characterRegisterReqDto);
            return "redirect:/character";
        } catch (CustomException e) {
            model.addAttribute("error", "캐릭터를 찾을 수 없습니다.");
            return "characterRegisterForm";
        }
    }

    /**
     * Read : 내 캐릭터 모두 보기
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
     * Read: 다른 사람 캐릭터 상세
     */
    @GetMapping("/character/info/{name}")
    public String characterInfo(Model model, @PathVariable("name") String name) {
        model.addAttribute("character", characterService.info(name));
        model.addAttribute("equipment", characterService.equipment(name,""));
        return "characterInfo";
    }

    /**
     * Read : 회원 검색
     */
    @GetMapping("/character/search")
    public String characterInfoOther(Model model, @RequestParam("name") String name, @RequestParam("world") String world) {
        try {
            CharacterInfoResDto characterInfoResDto = characterService.search(name, world);
            model.addAttribute("character", characterInfoResDto);
            model.addAttribute("equipment", characterService.equipment(name,world));
            return "characterInfo";
        } catch (CustomException e) {
            model.addAttribute("characterError", "캐릭터를 찾을 수 없습니다.");
            return "main";
        }
    }

    /**
     * 캐릭터 삭제
     */
    @GetMapping("/character/delete")
    public String deletePage(Model model) {
        List<Character> characters = findCharactersIfExists();
        model.addAttribute("characters", characters);
        return "characterDelete";
    }

    /**
     * Delete : 캐릭터 삭제 요청
     */
    @PostMapping("/character/delete/process")
    public String delete(@ModelAttribute("deleteDto") CharacterDeleteReqDto characterDeleteReqDto) {
        characterService.delete(characterDeleteReqDto);
        return "redirect:/character";
    }
}
