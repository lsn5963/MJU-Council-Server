package depth.mju.council.domain.committe.service;

import depth.mju.council.domain.committe.dto.res.CommitteeRes;
import depth.mju.council.domain.committe.entity.Committee;
import depth.mju.council.domain.committe.repository.CommitteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommitteeService {
    private final CommitteeRepository committeeRepository;

    @Transactional(readOnly = true)
    public List<CommitteeRes> getAllCommittees() {
        List<Committee> committees = committeeRepository.findAll();

        return committees.stream()
                .map(committee -> CommitteeRes.builder()
                        .committeeId(committee.getId())
                        .description(committee.getDescription())
                        .college(committee.getCollege())
                        .name(committee.getName())
                        .pageUrl(committee.getPageUrl())
                        .snsUrl(committee.getSnsUrl())
                        .imgUrl(committee.getImgUrl())
                        .build())
                .collect(Collectors.toList());
    }
}