package depth.mju.council.domain.alliance.controller;

import depth.mju.council.domain.alliance.service.AllianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alliances")
@RequiredArgsConstructor
public class AllianceController {
    private final AllianceService allianceService;
}
