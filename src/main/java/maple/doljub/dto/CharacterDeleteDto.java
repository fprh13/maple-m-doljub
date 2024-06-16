package maple.doljub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterDeleteDto {
    private String name;

    @Builder
    public CharacterDeleteDto(String name) {
        this.name = name;
    }
}
