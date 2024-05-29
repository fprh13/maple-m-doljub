package maple.doljub.dto.maple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class CharacterMapleResDto {
    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("world_name")
    private String worldName;

    @JsonProperty("character_date_create")
    private String characterDateCreate;

    @JsonProperty("character_date_last_login")
    private String characterDateLastLogin;

    @JsonProperty("character_date_last_logout")
    private String characterDateLastLogout;

    @JsonProperty("character_job_name")
    private String characterJobName;

    @JsonProperty("character_gender")
    private String characterGender;

    @JsonProperty("character_exp")
    private long characterExp;

    @JsonProperty("character_level")
    private int characterLevel;
}
