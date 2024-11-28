package depth.mju.council.domain.user.service;

import depth.mju.council.domain.user.dto.req.LoginReq;
import depth.mju.council.domain.user.dto.req.RegisterReq;
import depth.mju.council.domain.user.dto.res.JWTAuthResponse;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.config.JwtTokenProvider;
import depth.mju.council.global.error.DefaultException;
import depth.mju.council.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder pwdEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JWTAuthResponse login(LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginReq.getUsername(), loginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JWTAuthResponse token = jwtTokenProvider.generateToken(loginReq.getUsername(), authentication);
        return token;
    }

    public String register(RegisterReq registerReq) {

        // add check for username exists in db
        if (userRepository.existsByUsername(registerReq.getUsername())){
            throw new DefaultException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(registerReq.getUsername())
                .encryptedPwd(pwdEncoder.encode(registerReq.getPassword()))
                .build();
        userRepository.save(userEntity);

        return "User registered successfully!";
    }
}