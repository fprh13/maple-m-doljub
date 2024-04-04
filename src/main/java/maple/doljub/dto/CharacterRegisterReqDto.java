package maple.doljub.dto;

import lombok.*;
import maple.doljub.domain.Character;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class CharacterRegisterReqDto {
    private String name;
    private String world;

    @Builder
    private CharacterRegisterReqDto(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public static CharacterRegisterReqDto of(String name, String world){
        return CharacterRegisterReqDto.builder()
                .name(name)
                .world(world)
                .build();
    }
}
