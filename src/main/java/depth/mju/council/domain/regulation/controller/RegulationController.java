package depth.mju.council.domain.regulation.controller;

import depth.mju.council.domain.regulation.service.RegulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regulations")
@RequiredArgsConstructor
public class RegulationController {
    private final RegulationService regulationService;
}
