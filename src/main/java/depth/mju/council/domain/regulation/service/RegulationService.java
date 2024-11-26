package depth.mju.council.domain.regulation.service;

import depth.mju.council.domain.regulation.dto.res.RetrieveAllRegulationRes;
import depth.mju.council.domain.regulation.entity.Regulation;
import depth.mju.council.domain.regulation.repository.RegulationRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegulationService {
    private final UserRepository userRepository;
    private final RegulationRepository regulationRepository;
    @Transactional
    public void createRegulation(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).get();
        Regulation regulation = Regulation.builder()
                .fileUrl("파일 URL 저장 로직 필요")
                .user(user)
                .build();
        regulationRepository.save(regulation);
    }
    public List<RetrieveAllRegulationRes> retrieveAllRegulation(Long userId) {
        //페이징 추후 예정
        User user = userRepository.findById(userId).get();
        List<Regulation> regulations = regulationRepository.findByUser(user);
        List<RetrieveAllRegulationRes> regulationsRes = regulations.stream()
                .map(regulation -> RetrieveAllRegulationRes.builder()
                        .id(regulation.getId())
                        .imgUrl(regulation.getFileUrl())
                        .date(regulation.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return regulationsRes;
    }
    @Transactional
    public void modifyRegulation(Long regulationId, MultipartFile file) {
        Regulation regulation = regulationRepository.findById(regulationId).get();
        // 파일 URL 업데이트 로직 필요
        regulation.updateFileUrl("새로운 파일 URL");
    }
    @Transactional
    public void deleteRegulation(Long regulationId) {
        Regulation regulation = regulationRepository.findById(regulationId).get();
        regulationRepository.delete(regulation);
    }
}