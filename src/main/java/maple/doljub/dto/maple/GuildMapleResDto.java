package maple.doljub.dto.maple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildMapleResDto {
    @JsonProperty("guild_name")
    private String guildName;

}
