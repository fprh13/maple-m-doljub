package maple.doljub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maple.doljub.common.validation.ValidationGroups;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateReqDto {
    @NotBlank(message = "이메일이 입력되지 않았습니다.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 형식에 맞지 않습니다.",
            groups = ValidationGroups.PatternGroup.class)
    private String email;
    @NotBlank(message = "기존 비밀번호가 입력되지 않았습니다.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 숫자, 특수문자를 포함 해야 합니다.",
            groups = ValidationGroups.PatternGroup.class)
    private String password; // 기존 비밀번호
    @NotBlank(message = "변경 할 비밀번호가 입력되지 않았습니다.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 숫자, 특수문자를 포함 해야 합니다.",
            groups = ValidationGroups.PatternGroup.class)
    private String newPassword; // 새로운 비밀번호
    @NotBlank(message = "비밀번호 확인이 입력되지 않았습니다.", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 숫자, 특수문자를 포함 해야 합니다.",
            groups = ValidationGroups.PatternGroup.class)
    private String newPasswordCheck; // 새로운 비밀번호 확인


    @Builder
    public MemberUpdateReqDto(String email, String password, String newPassword, String newPasswordCheck) {
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
}
