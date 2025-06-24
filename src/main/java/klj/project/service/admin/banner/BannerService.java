package klj.project.service.admin.banner;

import java.util.List;

import org.springframework.stereotype.Service;

import klj.project.repository.banner.BannerQuerydslRepository;
import klj.project.web.dto.admin.banner.BannerResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BannerService {

	private final BannerQuerydslRepository bannerQuerydslRepository;
	
    public List<BannerResDto> getBannerList(){
        List<BannerResDto> boardMngrList = bannerQuerydslRepository.findAllBannerList();
        return boardMngrList;
    }
}
