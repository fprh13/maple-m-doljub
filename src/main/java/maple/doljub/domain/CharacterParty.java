package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 1.캐릭터가 가지고 있는 모든 파티를 본다.
 * 2. 파티가 가지고 있는 모든 캐릭터를 본다.
 */
@Entity
@Table(name = "character_party")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterParty {

    @Id @GeneratedValue
    @Column(name = "character_party_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "character_id")
    private Character character;

    // 추가 조회 사항

    /**
     * 연관 관계 메서드 - 파티 가입 메서드
     * 1. 파티 가입시 사용 된다.
     * */
    public void join(Party party, Character character) {
        this.party = party;
        this.character = character;
        party.getCharacterParties().add(this);
        character.getCharacterParties().add(this);
    }

}
