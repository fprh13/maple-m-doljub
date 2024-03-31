package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.dto.maple.CharacterMapleResDto;
import maple.doljub.config.RestTemplateClient;
import maple.doljub.domain.Character;
import maple.doljub.domain.Guild;
import maple.doljub.domain.User;
import maple.doljub.dto.CharacterDto;
import maple.doljub.repository.CharacterRepository;
import maple.doljub.repository.GuildRepository;
import maple.doljub.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final UserRepository userRepository;
    private final GuildRepository guildRepository;
    private final CharacterRepository characterRepository;
    private final RestTemplateClient restTemplateClient;

    /**
     * 캐릭터 등록
     */
    @Transactional
    public Long join(CharacterDto characterDto) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        // ocid 받아오기
        String ocid = restTemplateClient.getOcid(characterDto);
        // 캐릭터 정보 받아오기
        CharacterMapleResDto characterInfo = restTemplateClient.getCharacterInfo(ocid);
        // 캐릭터 객체 생성
        Character mapleCharacter = new Character(ocid,characterInfo.getCharacterName(),characterInfo.getCharacterJobName());
        // 길드 정보 받아오기
        String guildName = restTemplateClient.getGuildInfo(ocid);
        if (guildName == null) {
            guildName = "길드없음";
        }
        // 존재하는 길드 인지 확인하기
        Guild findGuild = guildRepository.findByName(guildName);
        if (findGuild == null) {
            // 없는 길드라면 새로운 길드 객체 생성
            Guild guild = new Guild(guildName);

            Character character = Character.createCharacter(mapleCharacter, user, guild);
            return characterRepository.save(character).getId();
        } else {
            // 있는 길드라면 찾아온 길드 주입
            Character character = Character.createCharacter(mapleCharacter, user, findGuild);
            return characterRepository.save(character).getId();
        }
    }

    /**
     * 캐릭터 정보
     * 꺼내오면 되요
     */
}
