package maple.doljub.service;

import maple.doljub.domain.User;
import maple.doljub.dto.SignupDto;
import maple.doljub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(SignupDto signupDto) {
        boolean isUser = userRepository.existsByUsername(signupDto.getUsername());
        if (isUser) {
            return;
        }
        User user = new User(
                signupDto.getUsername(),
                bCryptPasswordEncoder.encode(signupDto.getPassword()),
                signupDto.getEmail(),
                "ROLE_ADMIN");

        userRepository.save(user);
    }
}
