package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.common.exception.CustomException;
import maple.doljub.common.exception.ErrorCode;
import maple.doljub.domain.Member;
import maple.doljub.dto.*;
import maple.doljub.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(MemberSignUpReqDto memberSignUpReqDto) {
        Member member = Member.of(memberSignUpReqDto, bCryptPasswordEncoder.encode(memberSignUpReqDto.getPassword()));
        memberRepository.save(member);
    }

    /*로그인 아이디 중복*/
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    /**
     * 회원 정보 조회
     */
    public MemberResDto find(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        return MemberResDto.builder()
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .createDate(Date.from(member.getCreateDate().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByLoginId(loginId);

        // 비밀번호 업데이트가 포함 되는지
        boolean isPasswordChangeIncluded = isPasswordChangeIncluded(memberUpdateReqDto);

        if (isPasswordChangeIncluded) { // 비밀번호 업데이트가 포함 될 경우
            validatePasswordUpdate(memberUpdateReqDto, member);
            member.update(memberUpdateReqDto.getEmail(), bCryptPasswordEncoder.encode(memberUpdateReqDto.getNewPassword()));
        } else {
            member.update(memberUpdateReqDto.getEmail(), member.getPassword());
        }
    }

    /* 회원 수정 중 비밀번호 수정이 포함 되는지 확인 */
    private boolean isPasswordChangeIncluded(MemberUpdateReqDto memberUpdateReqDto) {
        return memberUpdateReqDto.getNewPassword() != null && !memberUpdateReqDto.getNewPassword().isEmpty() &&
                memberUpdateReqDto.getNewPasswordCheck() != null && !memberUpdateReqDto.getNewPasswordCheck().isEmpty();
    }

    /* 비밀번호 확인 및 인코딩 비밀번호 비교*/
    private void validatePasswordUpdate(MemberUpdateReqDto memberUpdateReqDto, Member member) {
        if (!memberUpdateReqDto.getNewPassword().equals(memberUpdateReqDto.getNewPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        if (!bCryptPasswordEncoder.matches(memberUpdateReqDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void delete(MemberDeleteReqDto memberDeleteReqDto) {
        Member member = memberRepository.findByLoginId(memberDeleteReqDto.getLoginId());
        // 비밀번호가 올바른지
        if (!bCryptPasswordEncoder.matches(memberDeleteReqDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        memberRepository.deleteById(member.getId());
    }
}
