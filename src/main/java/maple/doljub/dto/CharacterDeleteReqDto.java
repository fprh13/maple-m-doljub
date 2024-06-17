package maple.doljub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterDeleteReqDto {
    private String name;

    @Builder
    public CharacterDeleteReqDto(String name) {
        this.name = name;
    }
}
