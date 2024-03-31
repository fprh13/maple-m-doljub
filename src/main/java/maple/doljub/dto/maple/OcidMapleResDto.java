package maple.doljub.dto.maple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class OcidMapleResDto {

    @JsonProperty("ocid")
    private String ocid;

}
