package depth.mju.council.domain.department.service;

import depth.mju.council.domain.department.dto.res.DepartmentRes;
import depth.mju.council.domain.department.entity.Department;
import depth.mju.council.domain.department.repository.DepartmentRepository;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.error.DefaultException;
import depth.mju.council.global.payload.ErrorCode;
import depth.mju.council.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<DepartmentRes> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department -> DepartmentRes.builder()
                        .departmentId(department.getId())
                        .description(department.getDescription())
                        .imgUrl(department.getImgUrl())
                        .build())
                .collect(Collectors.toList());
    }

}