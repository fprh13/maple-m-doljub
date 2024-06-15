package maple.doljub.repository;

import maple.doljub.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    @Query("select m from Member m left join fetch m.characters where m.loginId = :loginId")
    Member findCharactersByLoginId(@Param("loginId") String loginId);


}
