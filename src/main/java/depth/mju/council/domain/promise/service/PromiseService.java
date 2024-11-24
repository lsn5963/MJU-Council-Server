package depth.mju.council.domain.promise.service;

import depth.mju.council.domain.promise.dto.res.PolicyResponse;
import depth.mju.council.domain.promise.entity.PromiseCategory;
import depth.mju.council.domain.promise.repository.PromiseCategoryRepository;
import depth.mju.council.domain.promise.repository.PromiseRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResponse;
import depth.mju.council.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromiseService {
    private final UserRepository userRepository;
    private final PromiseCategoryRepository promiseCategoryRepository;
    public ResponseEntity<?> createPromiseCategory(Long id, String policyTitle) {
        User user = userRepository.findById(id).get();
        PromiseCategory promiseCategory = PromiseCategory.builder()
                .title(policyTitle)
                .user(user)
                .build();
        promiseCategoryRepository.save(promiseCategory);
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("정책 목록을 추가했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> retrievePromiseCategory(Long id) {
        User user = userRepository.findById(id).get();
        List<PromiseCategory> promiseCategories = promiseCategoryRepository.findByUser(user);
        List<PolicyResponse> policies = promiseCategories.stream()
                .map(promiseCategory -> PolicyResponse.builder()
                        .id(promiseCategory.getId())
                        .title(promiseCategory.getTitle())
                        .build())
                .collect(Collectors.toList());

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(policies)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Transactional
    public ResponseEntity<?> modifyPromiseCategory(Long id, Long policyId, String policyTitle) {
        PromiseCategory promiseCategory = promiseCategoryRepository.findById(policyId).get();
        promiseCategory.updatePolicyTitle(policyTitle);
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("정책 목록을 수정했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> deletePromiseCategory(Long id, Long policyId) {
        PromiseCategory promiseCategory = promiseCategoryRepository.findById(policyId).get();
        promiseCategoryRepository.delete(promiseCategory);
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("정책 목록을 삭제했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}