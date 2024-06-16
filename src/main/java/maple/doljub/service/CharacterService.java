package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.common.util.EquipmentItemFilterUtil;
import maple.doljub.domain.Member;
import maple.doljub.dto.CharacterInfoResDto;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.dto.maple.CharacterMapleResDto;
import maple.doljub.common.config.RestTemplateClient;
import maple.doljub.domain.Character;
import maple.doljub.domain.Guild;
import maple.doljub.dto.maple.EquipmentItemDto;
import maple.doljub.repository.CharacterRepository;
import maple.doljub.repository.GuildRepository;
import maple.doljub.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterService {

    private final MemberRepository memberRepository;
    private final GuildRepository guildRepository;
    private final CharacterRepository characterRepository;
    private final RestTemplateClient restTemplateClient;

    /**
     * 캐릭터 등록
     */
    @Transactional
    public Long join(CharacterRegisterReqDto characterRegisterReqDto) {
        Member member = memberRepository.findByLoginId(SecurityContextHolder.getContext().getAuthentication().getName());
        // ocid 받아오기
        String ocid = restTemplateClient.getOcid(characterRegisterReqDto);
        // 캐릭터 정보 받아오기
        CharacterMapleResDto characterInfo = restTemplateClient.getCharacterInfo(ocid);
        // 캐릭터 객체 생성
        Character mapleCharacter = new Character(ocid,characterInfo.getCharacterName(),characterInfo.getCharacterJobName(), characterInfo.getWorldName());
        // 길드 정보 받아오기
        String guildName = restTemplateClient.getGuildInfo(ocid);
        if (guildName == null) {
            guildName = "길드없음";
        }
        // 존재하는 길드 인지 확인하기
        Guild findGuild = guildRepository.findByName(guildName);
        if (findGuild == null) {
            // 없는 길드라면 새로운 길드 객체 생성
            Guild guild = new Guild(guildName, characterInfo.getWorldName());

            Character character = Character.createCharacter(mapleCharacter, member, guild);
            return characterRepository.save(character).getId();
        } else {
            // 있는 길드라면 찾아온 길드 주입
            Character character = Character.createCharacter(mapleCharacter, member, findGuild);
            return characterRepository.save(character).getId();
        }
    }

    /**
     * 나의 캐릭터 정보
     */
    public Member findMyCharacters() {
        return memberRepository.findCharactersByLoginId(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * 캐릭터 정보
     */
    public CharacterInfoResDto info(String name) {
        Character character = characterRepository.findByName(name);
        String ocid = character.getNexonId();
        CharacterMapleResDto mapleResDto = restTemplateClient.getCharacterInfo(ocid);
        return new CharacterInfoResDto(mapleResDto,character.getGuild().getName());
    }

    /**
     * 캐릭터 장비 정보
     */
    public List<EquipmentItemDto> equipment(String name) {
        Character character = characterRepository.findByName(name);
        String ocid = character.getNexonId();
        List<EquipmentItemDto> allEquipmentItems = restTemplateClient.getEquipmentItem(ocid);
        return EquipmentItemFilterUtil.filter(allEquipmentItems); // 장비 필터링
    }


    public CharacterInfoResDto search(String name, String world) {
        CharacterRegisterReqDto characterRegisterReqDto = CharacterRegisterReqDto.builder()
                .name(name).world(world).build();
        String ocid = restTemplateClient.getOcid(characterRegisterReqDto);
        CharacterMapleResDto mapleResDto = restTemplateClient.getCharacterInfo(ocid);
        String guild = restTemplateClient.getGuildInfo(ocid);
        return new CharacterInfoResDto(mapleResDto,guild);
    }
}
