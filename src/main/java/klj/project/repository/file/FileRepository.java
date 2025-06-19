package klj.project.repository.file;

import klj.project.domain.file.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<Files, Long> {

    Optional<Files> findByFileGroupId(Long fileGroupId);
}
