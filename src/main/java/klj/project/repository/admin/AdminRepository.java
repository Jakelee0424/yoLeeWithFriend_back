package klj.project.repository.admin;


import klj.project.domain.admin.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    long count();

    Optional<Admin> findById(String id);


}
