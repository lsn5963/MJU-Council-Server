package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.service.PromiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promises")
@RequiredArgsConstructor
public class PromiseController {
    private final PromiseService promiseService;
}
