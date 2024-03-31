package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "party")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party {

    @Id @GeneratedValue
    @Column(name = "party_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<CharacterParty> characterParties = new ArrayList<>();


    private String name; // 파티 이름

    private int max; // 최대인원

    // 비숍 여부
    // 격 수 지정
    // 주/일 00시 출
    // 특정 날짜 시간 출
    // 보스 정하기



    /**
     * setter
     * 1. 연관 관계 주입 시 사용
     */
    protected void setGuild(Guild guild) {
        this.guild = guild;
    }
}
