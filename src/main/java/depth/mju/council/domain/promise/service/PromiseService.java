package depth.mju.council.domain.promise.service;

import depth.mju.council.domain.promise.dto.req.CreatePromiseReq;
import depth.mju.council.domain.promise.dto.req.ModifyPromiseReq;
import depth.mju.council.domain.promise.dto.res.PromiseRes;
import depth.mju.council.domain.promise.entity.Promise;
import depth.mju.council.domain.promise.entity.PromiseCategory;
import depth.mju.council.domain.promise.repository.PromiseCategoryRepository;
import depth.mju.council.domain.promise.repository.PromiseRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromiseService {
    private final UserRepository userRepository;
    private final PromiseCategoryRepository promiseCategoryRepository;
    private final PromiseRepository promiseRepository;
    public ResponseEntity<?> createPromise(Long id, String policyTitle, CreatePromiseReq createPromiseReq) {
        User user = userRepository.findById(id).get();
        // 정책 가져오기
        PromiseCategory promiseCategory = promiseCategoryRepository.findByUserAndTitle(user,policyTitle);
        // 공약 생성
        Promise promise = Promise.builder()
                .title(createPromiseReq.getTitle())
                .content(createPromiseReq.getContent())
                .progress(createPromiseReq.getProgress())
                .promiseCategory(promiseCategory)
                .build();

        promiseRepository.save(promise);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("공약을 추가했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> retrievePromise(Long id, String policyTitle) {
        User user = userRepository.findById(id).get();
        PromiseCategory promiseCategory = promiseCategoryRepository.findByUserAndTitle(user, policyTitle);
        List<Promise> promises = promiseRepository.findByPromiseCategory(promiseCategory);
        List<PromiseRes> promisesRes = promises.stream()
                .map(promise -> PromiseRes.builder()
                        .id(promise.getId())
                        .title(promise.getTitle())
                        .content(promise.getContent())
                        .progress(promise.getProgress())
                        .build())
                .collect(Collectors.toList());


        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(promisesRes)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> deletePromise(Long promiseId) {
        Promise promise = promiseRepository.findById(promiseId).get();
        promiseRepository.delete(promise);
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("공약을 삭제했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Transactional
    public ResponseEntity<?> modifyPromise(Long promiseId, ModifyPromiseReq modifyPromiseReq) {
        Promise promise = promiseRepository.findById(promiseId).get();
        promise.update(modifyPromiseReq);
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("공약을 수정했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}