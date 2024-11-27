package depth.mju.council.global.config;

import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다"));

        // 여기서 return하는 user는 직접 생성한 User 엔티티가 아닌 security 라이브러리의 User
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getEncryptedPwd())
                .authorities(new SimpleGrantedAuthority("ADMIN"))
                .build();
    }

    public Long findUserIdByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다"));
        return userEntity.getId();
    }

}
