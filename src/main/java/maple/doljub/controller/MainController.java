package maple.doljub.controller;

import maple.doljub.domain.Character;
import maple.doljub.domain.User;
import maple.doljub.service.CharacterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    private final CharacterService characterService;

    public MainController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/")
    public String main(Model model) {
        User user = characterService.findMyCharacters();
        if (user != null) {
            List<Character> characters = user.getCharacters();
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
