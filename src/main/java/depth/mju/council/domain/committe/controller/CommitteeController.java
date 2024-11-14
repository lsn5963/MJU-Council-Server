package depth.mju.council.domain.committe.controller;

import depth.mju.council.domain.committe.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/committees")
@RequiredArgsConstructor
public class CommitteeController {
    private final CommitteeService committeeService;
}
