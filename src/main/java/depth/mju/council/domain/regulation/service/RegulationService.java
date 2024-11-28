package depth.mju.council.domain.regulation.service;

import depth.mju.council.domain.minute.dto.res.GetAllMinuteRes;
import depth.mju.council.domain.minute.dto.res.GetMinuteRes;
import depth.mju.council.domain.minute.entity.Minute;
import depth.mju.council.domain.regulation.dto.res.GetAllRegulationRes;
import depth.mju.council.domain.regulation.dto.res.GetRegulationRes;
import depth.mju.council.domain.regulation.entity.Regulation;
import depth.mju.council.domain.regulation.repository.RegulationRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegulationService {
    private final UserRepository userRepository;
    private final RegulationRepository regulationRepository;
    @Transactional
    public void createRegulation(Long userId, MultipartFile file, LocalDateTime revisionDate) {
        User user = userRepository.findById(userId).get();
        //
        Regulation regulation = Regulation.builder()
                .fileUrl("파일 URL 저장 로직 필요")
                .revisionDate(revisionDate)
                .user(user)
                .build();
        regulationRepository.save(regulation);
    }
    public PageResponse getAllRegulation(Optional<String> keyword, int page, int size) {
        //페이징 추후 예정
//        User user = userRepository.findById(userId).get();
//        List<Regulation> regulations = regulationRepository.findByUser(user);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Regulation> pageResult;
        if (keyword.isPresent()) {
            pageResult = regulationRepository.findByTitleContaining(keyword.get(), pageRequest);
        } else {
            pageResult = regulationRepository.findAll(pageRequest);
        }
        return PageResponse.builder()
                .totalElements(pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .pageSize(pageResult.getSize())
                .contents(pageResult.getContent().stream()
                        .map(regulation -> GetAllRegulationRes.builder()
                                .regulationId(regulation.getId())
                                .imgUrl(regulation.getFileUrl())
                                .date(regulation.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
    public GetRegulationRes getRegulation(Long regulationId) {
        Regulation regulation = regulationRepository.findById(regulationId).get();
        return GetRegulationRes.builder()
                .regulationId(regulation.getId())
                .imgUrl(regulation.getFileUrl())
                .date(regulation.getCreatedAt())
                .build();

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