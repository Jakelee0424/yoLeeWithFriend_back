package klj.project.service.admin.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import klj.project.domain.banner.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    long count();


}