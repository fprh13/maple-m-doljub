package maple.doljub.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@Setter
public class MemberSignUpReqDto {

    private String loginId;
    private String password;
    private String email;

    @Builder
    public MemberSignUpReqDto(String loginId, String password, String email) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

}
