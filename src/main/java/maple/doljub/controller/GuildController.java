package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.common.exception.CustomException;
import maple.doljub.domain.Character;
import maple.doljub.domain.Guild;
import maple.doljub.dto.CharacterInfoResDto;
import maple.doljub.service.GuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @GetMapping("/guild/search")
    public String characterInfoOther(Model model, RedirectAttributes redirectAttributes , @RequestParam("name") String name) {
        Guild guild = guildService.guildSearch(name);
        if (guild != null) {
            String encodedName = URLEncoder.encode(guild.getName(), StandardCharsets.UTF_8);
            return "redirect:/guild/info/" + encodedName;

        } else {
            redirectAttributes.addFlashAttribute("guildError", "해당 길드를 찾을 수 없습니다.");
            return "redirect:/";
        }
    }
}
