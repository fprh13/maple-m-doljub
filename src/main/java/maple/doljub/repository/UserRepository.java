package maple.doljub.repository;

import maple.doljub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByUsername(String username);


    @Query("select u from User u left join fetch u.characters where u.username = :username")
    User findCharactersByUsername(@Param("username") String username);

}
