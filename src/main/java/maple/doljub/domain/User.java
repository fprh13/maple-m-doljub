package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 회원가입 시 나의 캐릭터를 생성한다.
 */
@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // 캐릭터 조회용
    private List<Character> characters = new ArrayList<>();

    private String LoginId;

    private String password;

    private String nickName;

    public User(String loginId, String password, String nickName) {
        LoginId = loginId;
        this.password = password;
        this.nickName = nickName;
    }
}
