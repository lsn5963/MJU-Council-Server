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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MinuteService {
    private final UserRepository userRepository;
    private final MinuteRepository minuteRepository;
    public String createMinute(Long id, List<MultipartFile> imgs, CreateMinuteReq createMinuteReq) {
        User user = userRepository.findById(id).get();
        // 회의록 저장 로직 필요
        Minute minute = Minute.builder()
                .title(createMinuteReq.getTitle())
                .content(createMinuteReq.getContent())
                .user(user)
                .build();
        minuteRepository.save(minute);
        return "공약을 삭제했어요";
    }
    public ResponseEntity<?> retrieveAllMinute(Long id) {
        User user = userRepository.findById(id).get();
        List<Minute> minutes = minuteRepository.findByUser(user);
        List<RetrieveAllMinuteRes> minutesRes = minutes.stream()
                .map(minute -> RetrieveAllMinuteRes.builder()
                        .id(minute.getId())
                        .writer(user.getName())
                        .title(minute.getTitle())
                        .date(minute.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minutesRes)
                .build();
        return ResponseEntity.ok(result);
    }
    public ResponseEntity<?> retrieveMinute(Long id) {
        User user = userRepository.findById(id).get();
        List<Minute> minutes = minuteRepository.findByUser(user);
        List<RetrieveMinuteRes> minutesRes = minutes.stream()
                .map(minute -> RetrieveMinuteRes.builder()
                        .id(minute.getId())
                        .writer(user.getName())
                        .title(minute.getTitle())
                        .content(minute.getContent())
                        .date(minute.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minutesRes)
                .build();
        return ResponseEntity.ok(result);
    }
    @Transactional
    public ResponseEntity<?> modifyMinute(Long minuteId, ModifyMinuteReq modifyMinuteReq, List<MultipartFile> imgs) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minute.update(modifyMinuteReq);
        //사진 수정 로직 필요

        ApiResult result = ApiResult.builder()
                .check(true)
                .information("회의록을 수정했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    public ResponseEntity<?> deleteMinute(Long minuteId) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minuteRepository.delete(minute);

        ApiResult result = ApiResult.builder()
                .check(true)
                .information("회의록을 삭제했어요")
                .build();
        return ResponseEntity.ok(result);
    }
}