package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Member;
import maple.doljub.dto.CustomUserDetails;
import maple.doljub.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member memberData = memberRepository.findByLoginId(loginId);

        if (memberData != null) {
            return CustomUserDetails.from(memberData);
        }
        return null;
    }
}
