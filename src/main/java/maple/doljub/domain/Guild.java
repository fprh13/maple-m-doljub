package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maple.doljub.common.auditing.BaseCreateByEntity;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "guild")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild extends BaseCreateByEntity {

    @Id @GeneratedValue
    @Column(name = "guild_id")
    private Long id;

    // persist 이유
    // All로 하면 길드가 삭제 되었을 때 캐릭터가 전부 삭제가 된다.
    @OneToMany(mappedBy = "guild") // guild 원 조회
    private List<Character> characters = new ArrayList<>();

    @OneToMany(mappedBy = "guild", cascade = CascadeType.ALL) // guild 파티 목록 조회
    private List<Party> parties = new ArrayList<>();

    private String name; // 길드 이름

    private String world; // 월드 이름


    public Guild(String name, String world) {
        this.name = name;
        this.world = world;
    }

    /**
     * 연관 관계 메서드
     * 1. 길드에서 파티를 생성 할 때 사용 한다.
     */
    public void addParty(Party party) {
        parties.add(party);
        party.setGuild(this);
    }

}
