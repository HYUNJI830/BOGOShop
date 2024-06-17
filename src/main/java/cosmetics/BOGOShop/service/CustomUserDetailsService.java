package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."+ userId));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                //.password(passwordEncoder.matches(member.getPassword(),passwordEncoder.encode(member.getPassword()))
                //.roles(member.getRoles().toArray(new String[0]))
                .roles(String.valueOf(member.getRoles()))
                .build();
    }
}
