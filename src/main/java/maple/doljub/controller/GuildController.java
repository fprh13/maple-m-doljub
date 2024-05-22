package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Character;
import maple.doljub.domain.Guild;
import maple.doljub.service.GuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GuildController {

    private final GuildService guildService;

    @GetMapping("/guild")
    public String guildList(Model model) {
        model.addAttribute("guilds", guildService.guildList());
        return "guild";
    }

    @GetMapping("/guild/info/{name}")
    public String guildInfo(Model model, @PathVariable("name") String name) {
        Guild guild = guildService.guildInfo(name);
        if (guild != null) {
            List<Character> characters = guild.getCharacters();
            if (characters != null) {
                model.addAttribute("guild", guild);
                model.addAttribute("characters", characters);
            } else {
                model.addAttribute("characters", Collections.emptyList()); // Empty list if characters are null
            }
        } else {
            model.addAttribute("characters", Collections.emptyList()); // Empty list if user is null
        }
        return "guildInfo";
    }
}
