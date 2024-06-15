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
    public String main() {
        return "main";
    }

}
