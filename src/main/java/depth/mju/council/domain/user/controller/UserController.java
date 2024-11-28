package depth.mju.council.domain.user.controller;

import depth.mju.council.domain.user.dto.req.LoginReq;
import depth.mju.council.domain.user.dto.req.RegisterReq;
import depth.mju.council.domain.user.dto.res.JWTAuthResponse;
import depth.mju.council.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(
        JWTAuthResponse token = userService.login(loginReq);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
        String response = userService.register(registerReq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}