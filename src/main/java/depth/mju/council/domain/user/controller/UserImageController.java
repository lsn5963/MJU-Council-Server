package depth.mju.council.domain.user.controller;

import depth.mju.council.domain.user.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-images")
@RequiredArgsConstructor
public class UserImageController {
    private final UserImageService userImageService;
}
