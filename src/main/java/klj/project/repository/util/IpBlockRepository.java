package klj.project.repository.util;

import klj.project.domain.util.IpBlock;
import klj.project.domain.util.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpBlockRepository extends JpaRepository<IpBlock, Long> {

    // ipAddress로 찾기
    Optional<IpBlock> findByIpAddress(String ipAddress);

    // 필요하다면, 존재 여부만 확인
    boolean existsByIpAddress(String ipAddress);
}
