package depth.mju.council.domain.minute.service;

import depth.mju.council.domain.minute.dto.req.CreateMinuteReq;
import depth.mju.council.domain.minute.dto.req.ModifyMinuteReq;
import depth.mju.council.domain.minute.dto.res.RetrieveMinuteRes;
import depth.mju.council.domain.minute.entity.Minute;
import depth.mju.council.domain.minute.repository.MinuteRepository;
import depth.mju.council.domain.minute.dto.res.RetrieveAllMinuteRes;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MinuteService {
    private final UserRepository userRepository;
    private final MinuteRepository minuteRepository;
    @Transactional
    public void createMinute(Long id, List<MultipartFile> imgs, CreateMinuteReq createMinuteReq) {
        User user = userRepository.findById(id).get();
        // 회의록 저장 로직 필요
        Minute minute = Minute.builder()
                .title(createMinuteReq.getTitle())
                .content(createMinuteReq.getContent())
                .user(user)
                .build();
        minuteRepository.save(minute);
    }
    public List<RetrieveAllMinuteRes> retrieveAllMinute(Long id) {
        User user = userRepository.findById(id).get();
        List<Minute> minutes = minuteRepository.findByUser(user);
        return minutes.stream()
                .map(minute -> RetrieveAllMinuteRes.builder()
                        .id(minute.getId())
                        .writer(user.getName())
                        .title(minute.getTitle())
                        .date(minute.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
    public RetrieveMinuteRes retrieveMinute(Long userId, Long minuteId) {
        User user = userRepository.findById(userId).get();
        Minute minutes = minuteRepository.findById(minuteId).get();
        return RetrieveMinuteRes.builder()
                .id(minutes.getId())
                .writer(user.getName())
                .title(minutes.getTitle())
                .content(minutes.getContent())
                .date(minutes.getCreatedAt())
                .build();
    }
    @Transactional
    public void modifyMinute(Long minuteId, ModifyMinuteReq modifyMinuteReq, List<MultipartFile> imgs) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minute.update(modifyMinuteReq);
        //사진 수정 로직 필요
    }
    @Transactional
    public void deleteMinute(Long minuteId) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minuteRepository.delete(minute);
    }
}