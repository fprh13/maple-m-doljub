package maple.doljub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import maple.doljub.common.util.JobTranslator;
import maple.doljub.dto.maple.CharacterMapleResDto;

@Getter
@NoArgsConstructor
public class CharacterInfoResDto {

    private String name;
    private String guild;
    private String world;
    private String job;
    private String jobEnglishName;
    private String gender;
    private long exp;
    private long level;
    private String recentPlay;

    public CharacterInfoResDto(CharacterMapleResDto characterMapleResDto, String guild) {
        this.name = characterMapleResDto.getCharacterName();
        this.guild = guild;
        this.world = characterMapleResDto.getWorldName();

        // 아크메이지 직업군 대비 replace 진행
        String replaceJob = characterMapleResDto.getCharacterJobName().replaceAll("[(),]", "");
        this.job = replaceJob;
        this.jobEnglishName = JobTranslator.valueOf(replaceJob).getEnglishName();

        this.gender = characterMapleResDto.getCharacterGender();
        this.exp = characterMapleResDto.getCharacterExp();
        this.level = characterMapleResDto.getCharacterLevel();

        // 오랜된 유저의 로그인 시간 누락 방지
        String dateLastLogin = characterMapleResDto.getCharacterDateLastLogin();
        if (dateLastLogin == null || dateLastLogin.isEmpty()) {
            this.recentPlay = "-";
        } else {
            this.recentPlay = characterMapleResDto.getCharacterDateLastLogin().substring(0, 10); // 년월일만 자르기
        }
    }



}
