package maple.doljub.controller;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Character;
import maple.doljub.domain.Guild;
import maple.doljub.service.GuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GuildController {

    private final GuildService guildService;

    /**
     * Read: 길드 목록
     */
    @GetMapping("/guild")
    public String guildList(Model model) {
        model.addAttribute("guilds", guildService.guildList());
        return "guild";
    }

    /**
     * Read : 길드원 조회
     */
    @GetMapping("/guild/info/{name}")
    public String guildInfo(Model model, @PathVariable("name") String name) {
        Guild guild = guildService.guildInfo(name);
        List<Character> characters = (guild != null) ? guild.getCharacters() : Collections.emptyList();
        model.addAttribute("guild", guild);
        model.addAttribute("characters", characters);
        return "guildInfo";
    }

    /**
     * Read : 길드 검색
     */
    @GetMapping("/guild/search")
    public String characterInfoOther(Model model, @RequestParam("name") String name) {
        Guild guild = guildService.guildSearch(name);
        if (guild != null) {
            String encodedName = URLEncoder.encode(guild.getName(), StandardCharsets.UTF_8);
            return "redirect:/guild/info/" + encodedName;
        } else {
            model.addAttribute("guildError", "해당 길드를 찾을 수 없습니다.");
            return "main";
        }
    }
}
