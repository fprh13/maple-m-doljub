package maple.doljub.repository;

import maple.doljub.domain.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<Guild, Long> {

    Guild findByName(String name);

    boolean existsByName(String name);

}
