package maple.doljub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maple.doljub.dto.maple.CharacterMapleResDto;

@Getter
@NoArgsConstructor
public class CharacterInfoResDto {

    private String name;
    private String guild;
    private String world;
    private String job;
    private String gender;
    private long exp;
    private long level;
    private String recentPlay;

    public CharacterInfoResDto(CharacterMapleResDto characterMapleResDto, String guild) {
        this.name = characterMapleResDto.getCharacterName();
        this.guild = guild;
        this.world = characterMapleResDto.getWorldName();
        this.job = characterMapleResDto.getCharacterJobName();
        this.gender = characterMapleResDto.getCharacterGender();
        this.exp = characterMapleResDto.getCharacterExp();
        this.level = characterMapleResDto.getCharacterLevel();
        this.recentPlay = characterMapleResDto.getCharacterDateLastLogin().substring(0, 10); // 년 분 월 만 짜르기
    }

}
