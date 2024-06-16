package maple.doljub.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maple.doljub.common.auditing.BaseCreateByEntity;
import maple.doljub.common.util.JobTranslator;

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
public class Character extends BaseCreateByEntity {

    @Id
    @GeneratedValue
    @Column(name = "character_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id") // 연관 관계 주인
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 캐릭터 저장 시 길드를 함꼐 저장
    @JoinColumn(name = "guild_id")
    private Guild guild;

    // onToMany character 양방향 or 단방향으로 설정 (캐릭터가 캐릭터-파티 에서 조회 할 것이 있다면 양방향)

    @OneToMany(mappedBy = "character")
    private List<CharacterParty> characterParties = new ArrayList<>();

    private String nexonId; // nexon 캐릭터 고유 식별자

    private String name;

    private String job;

    private String jobEnglishName;

    private String world;

    public Character(String nexonId, String name, String job, String world) {
        this.nexonId = nexonId;
        this.name = name;
        this.world = world;

        // 아크메이지 직업군 대비 replace 진행
        String replaceJob = job.replaceAll("[(),]", "");

        this.job = replaceJob;
        this.jobEnglishName = JobTranslator.valueOf(replaceJob).getEnglishName();
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
     */
    public void setMember(Member member) {
        this.member = member;
        member.getCharacters().add(this);
    }

    public static Character createCharacter(Character character, Member member, Guild guild) {
        Character newCharacter = new Character(character.getNexonId(), character.getName(), character.getJob(), character.getWorld());
        newCharacter.setGuild(guild);
        newCharacter.setMember(member);
        return newCharacter;
    }
}
