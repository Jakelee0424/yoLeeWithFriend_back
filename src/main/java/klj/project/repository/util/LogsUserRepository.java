package klj.project.repository.util;

import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsUserRepository extends JpaRepository<LogsUser, Long> {
}
