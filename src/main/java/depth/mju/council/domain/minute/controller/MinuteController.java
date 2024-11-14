package depth.mju.council.domain.minute.controller;

import depth.mju.council.domain.minute.service.MinuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/minutes")
@RequiredArgsConstructor
public class MinuteController {
    private final MinuteService minuteService;
}