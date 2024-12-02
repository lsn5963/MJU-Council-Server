package depth.mju.council.domain.minute.service;

import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.minute.dto.req.CreateMinuteReq;
import depth.mju.council.domain.minute.dto.req.ModifyMinuteReq;
import depth.mju.council.domain.minute.dto.res.GetMinuteFileRes;
import depth.mju.council.domain.minute.dto.res.GetMinuteRes;
import depth.mju.council.domain.minute.entity.Minute;
import depth.mju.council.domain.minute.entity.MinuteFile;
import depth.mju.council.domain.minute.repository.MinuteFileRepository;
import depth.mju.council.domain.minute.repository.MinuteRepository;
import depth.mju.council.domain.minute.dto.res.GetAllMinuteRes;
import depth.mju.council.domain.notice.dto.res.FileRes;
import depth.mju.council.domain.notice.entity.Notice;
import depth.mju.council.domain.notice.entity.NoticeFile;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResult;
import depth.mju.council.global.payload.PageResponse;
import depth.mju.council.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final MinuteFileRepository minuteFileRepository;
    private final S3Service s3Service;
    @Transactional
    public void createMinute(Long id, List<MultipartFile> imgs, CreateMinuteReq createMinuteReq) {
        UserEntity user = userRepository.findById(id).get();
        Minute minute = Minute.builder()
                .title(createMinuteReq.getTitle())
                .content(createMinuteReq.getContent())
                .userEntity(user)
                .build();
        minuteRepository.save(minute);
        uploadMinuteFiles(imgs, minute);
    }
    public PageResponse  getAllMinute(Optional<String> keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Minute> pageResult;
        if (keyword.isPresent()) {
            pageResult = minuteRepository.findByTitleContaining(keyword.get(), pageRequest);
        } else {
            pageResult = minuteRepository.findAll(pageRequest);
        }
        return PageResponse.builder()
                .totalElements(pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .pageSize(pageResult.getSize())
                .contents(pageResult.getContent().stream()
                        .map(getMinute -> GetAllMinuteRes.builder()
                                .minuteId(getMinute.getId())
                                .title(getMinute.getTitle())
                                .date(getMinute.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
    public GetMinuteRes getMinute(Long minuteId) {
        Minute minutes = minuteRepository.findById(minuteId).get();
        List<MinuteFile> minuteFiles = minuteFileRepository.findByMinute(minutes);

        List<GetMinuteFileRes> getMinuteFileRes = minuteFiles.stream()
                .map(file -> GetMinuteFileRes.builder()
                        .minuteFileId(file.getId())
                        .fileUrl(file.getFileUrl())
                        .fileName(file.getFileName())
                        .build())
                .collect(Collectors.toList());

        return GetMinuteRes.builder()
                .minuteId(minutes.getId())
//                .writer(user.getName())
                .title(minutes.getTitle())
                .content(minutes.getContent())
                .date(minutes.getCreatedAt())
                .files(getMinuteFileRes)
                .build();
    }
    @Transactional
    public void modifyMinute(Long minuteId, ModifyMinuteReq modifyMinuteReq, List<MultipartFile> imgs) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minute.update(modifyMinuteReq);
        deleteMinuteFiles(modifyMinuteReq.getDeleteFiles(), FileType.FILE);
        uploadMinuteFiles(imgs, minute);
    }
    @Transactional
    public void deleteMinute(Long minuteId) {
        Minute minute = minuteRepository.findById(minuteId).get();
        minuteRepository.delete(minute);
    }
    private void uploadMinuteFiles(List<MultipartFile> files, Minute minute) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveUploadFiles(s3Service.uploadFile(file), file.getOriginalFilename(), minute);
            }
        }
    }
    private void saveUploadFiles(String fileUrl, String originalFileName,Minute minute) {
        minuteFileRepository.save(MinuteFile.builder()
                .fileUrl(fileUrl)
                .fileName(originalFileName)
                .minute(minute)
                .build());
    }
    private String extractSaveFileName(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1];
    }
    private void deleteMinuteFiles(List<Integer> files, FileType fileType) {
        if (files == null || files.isEmpty()) {
            return;
        }
        List<Long> fileIds = files.stream().map(Long::valueOf).collect(Collectors.toList());
        List<MinuteFile> filesToDelete = minuteFileRepository.findAllById(fileIds);
        filesToDelete.forEach(file -> {
            // 저장 파일명 구하기
            String saveFileName = extractSaveFileName(file.getFileUrl());
            // S3에서 삭제
            if (fileType == FileType.FILE) {
                s3Service.deleteFile(saveFileName);
            } else {
                s3Service.deleteImage(saveFileName);
            }
            // DB에서 삭제
            minuteFileRepository.delete(file);
        });
    }
}