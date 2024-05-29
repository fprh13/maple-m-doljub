package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Member;
import maple.doljub.dto.MemberSignUpReqDto;
import maple.doljub.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(MemberSignUpReqDto memberSignUpReqDto) {
        boolean isMember = memberRepository.existsByLoginId(memberSignUpReqDto.getLoginId());
        if (isMember) {
            return;
        }
        Member member = Member.of(memberSignUpReqDto, bCryptPasswordEncoder.encode(memberSignUpReqDto.getPassword()));
        memberRepository.save(member);
    }


}
