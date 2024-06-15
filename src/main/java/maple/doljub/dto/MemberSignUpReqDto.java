package maple.doljub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import maple.doljub.common.validation.ValidationGroups;

import static maple.doljub.common.validation.ValidationGroups.*;

@Getter
@NoArgsConstructor
@ToString
@Setter
public class MemberSignUpReqDto {

    @NotBlank(message = "아이디가 입력되지 않았습니다.", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.",
            groups = PatternGroup.class)
    private String loginId;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.", groups = NotBlankGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 숫자, 특수문자를 포함 해야 합니다.",
            groups = PatternGroup.class)
    private String password;

    @NotBlank(message = "이메일이 입력되지 않았습니다.", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 형식에 맞지 않습니다.",
            groups = PatternGroup.class)
    private String email;

    @Builder
    public MemberSignUpReqDto(String loginId, String password, String email) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

}
