package maple.doljub.repository;

import maple.doljub.domain.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuildRepository extends JpaRepository<Guild, Long> {

    Guild findByName(String name);

    boolean existsByName(String name);

    @Query("select g from Guild g left join fetch g.characters where g.name = :name")
    Guild findCharactersByName(@Param("name") String name);

    @Query("select g from Guild g where g.name != '길드없음'")
    List<Guild> findGuildAll();

}
