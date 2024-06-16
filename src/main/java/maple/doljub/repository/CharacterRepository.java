package maple.doljub.repository;

import maple.doljub.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Character findByName(String name);
    boolean existsByName(String name);
}
