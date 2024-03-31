package maple.doljub.service;

import maple.doljub.domain.Character;
import maple.doljub.domain.User;
import maple.doljub.repository.CharacterRepository;
import maple.doljub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    private TestService testService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CharacterRepository characterRepository;

//    private CharacterRepository characterRepository;

//    @Transactional
    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //회원가입 진행
        System.out.println(testService.join());
        // 회원이 가지고 있는 모든 캐릭터를 가지고 오는 경우에도 가용
        User user = userRepository.findById(testService.join()).get();
        System.out.println(user.getCharacters().stream()
                .map(character -> character.getName())
                .collect(Collectors.toList()));
        // 길드 정보 조회



        assertTrue(true);
    }

}