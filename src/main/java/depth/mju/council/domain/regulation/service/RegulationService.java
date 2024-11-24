package depth.mju.council.domain.regulation.service;

import depth.mju.council.domain.regulation.entity.Regulation;
import depth.mju.council.domain.regulation.repository.RegulationRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegulationService {
    private final UserRepository userRepository;
    private final RegulationRepository regulationRepository;

    public ResponseEntity<?> createRegulation(Long id, MultipartFile file) {
        User user = userRepository.findById(id).get();
        Regulation regulation = Regulation.builder()
                .fileUrl("파일 URL 저장 로직 필요")
                .user(user)
                .build();
        regulationRepository.save(regulation);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("학생회칙을 추가했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    public ResponseEntity<?> retrieveAllRegulation(Long id) {
        User user = userRepository.findById(id).get();
        List<Regulation> regulations = regulationRepository.findByUser(user);
        List<RetrieveAllRegulationRes> regulationsRes = regulations.stream()
                .map(regulation -> RetrieveAllRegulationRes.builder()
                        .id(regulation.getId())
                        .fileUrl(regulation.getFileUrl())
                        .date(regulation.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(regulationsRes)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Transactional
    public ResponseEntity<?> modifyRegulation(Long regulationId, MultipartFile file) {
        Regulation regulation = regulationRepository.findById(regulationId).get();
        // 파일 URL 업데이트 로직 필요
        regulation.updateFileUrl("새로운 파일 URL");

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("학생회칙을 수정했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> deleteRegulation(Long regulationId) {
        Regulation regulation = regulationRepository.findById(regulationId).get();
        regulationRepository.delete(regulation);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("학생회칙을 삭제했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}