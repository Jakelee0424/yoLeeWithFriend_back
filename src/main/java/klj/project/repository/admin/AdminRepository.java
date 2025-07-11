package klj.project.repository.admin;

import klj.project.domain.admin.Admin;
import klj.project.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    long count();

    Optional<Admin> findById(String id);


}
