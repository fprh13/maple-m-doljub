package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maple.doljub.common.auditing.BaseCreateByEntity;
import maple.doljub.dto.MemberSignUpReqDto;

import java.util.ArrayList;
import java.util.List;

import static maple.doljub.domain.Role.ROLE_USER;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseCreateByEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "loginId", nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // 캐릭터 조회용
    private List<Character> characters = new ArrayList<>();

    @Builder
    private Member(String loginId, String password, String email, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.role = (role != null) ? role : ROLE_USER;
    }

    public static Member of(MemberSignUpReqDto requestDto, String encodedPassword) {
        return Member.builder()
                .loginId(requestDto.getLoginId())
                .password(encodedPassword)
                .email(requestDto.getEmail())
                .build();
    }

    /* 사용자 정보 수정 */
    public void update(String email,String password) {
        this.email = email;
        this.password = password;
    }
}
