package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 캐릭터를 생성할 시 길드에 가입 or 생성
 * 2. 길드가 없다면 길드 1.을 수행 하지 않는다.
 */
@Entity
@Table(name = "character")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @GeneratedValue
    @Column(name = "character_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") // 연관 관계 주인
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 캐릭터 저장 시 길드를 함꼐 저장
    @JoinColumn(name = "guild_id")
    private Guild guild;

    // onToMany character 양방향 or 단방향으로 설정 (캐릭터가 캐릭터-파티 에서 조회 할 것이 있다면 양방향)

    @OneToMany(mappedBy = "character")
    private List<CharacterParty> characterParties = new ArrayList<>();

    private String nexonId; // nexon 캐릭터 고유 식별자

    private String name;

    private String job;

    public Character(String nexonId, String name, String job) {
        this.nexonId = nexonId;
        this.name = name;
        this.job = job;
    }

    /**
     * 연관관계 메서드
     * 1. 회원가입 시 캐릭터 등록이 될 때 없는 길드라면 길드를 생성하기 위해 사용된다.
     * 2. 회원가입 시 캐릭터 등록이 될 때 있는 길드라면 길드에 가입하기 위해 사용된다.
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
        guild.getCharacters().add(this);
    }


    /**
     * Setter
     * 연관관계 주입 시 에만 사용 된다.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        user.getCharacters().add(this);
    }

    public static Character createCharacter(Character character, User user, Guild guild) {
        Character newCharacter = new Character(character.getNexonId(), character.getName(), character.getJob());
        newCharacter.setGuild(guild);
        newCharacter.setUser(user);
        return newCharacter;
    }
}
