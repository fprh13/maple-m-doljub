package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Character;
import maple.doljub.domain.Member;
import maple.doljub.service.CharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CharacterService characterService;

    @GetMapping("/")
    public String main(Model model) {
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
        return "main";
    }

}
