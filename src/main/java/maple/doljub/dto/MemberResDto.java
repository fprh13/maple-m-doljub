package maple.doljub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberResDto {
    private String loginId;
    private String email;
    private Date createDate;

    @Builder
    public MemberResDto(String loginId, String email, Date createDate) {
        this.loginId = loginId;
        this.email = email;
        this.createDate = createDate;
    }
}

