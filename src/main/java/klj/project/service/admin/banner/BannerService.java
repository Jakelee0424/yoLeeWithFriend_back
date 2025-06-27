package klj.project.service.admin.banner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import klj.project.domain.banner.Banner;
import klj.project.domain.board.Board;
import klj.project.repository.banner.BannerQuerydslRepository;
import klj.project.web.dto.admin.banner.BannerResDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BannerService {

	private final BannerRepository bannerRepository;
	private final BannerQuerydslRepository bannerQuerydslRepository;
	
    public List<BannerResDto> getBannerList(){
        List<BannerResDto> boardMngrList = bannerQuerydslRepository.findAllBannerList();
        return boardMngrList;
    }
    
	public List<BannerResDto> getExpiredBannerList() {
		List<BannerResDto> boardMngrList = bannerQuerydslRepository.findExpiredBannerList();
		return boardMngrList;
	}

	public List<BannerResDto> deleteBannerById(List<Long> bannerIds) {
		 
		for(int i=0; i< bannerIds.size(); i++){
	            Long bannerId = bannerIds.get(i);
	            Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new IllegalArgumentException("해당 배너를 찾을 수 없습니다. ID: " + bannerId));
	            banner.deleteBanner();
	            bannerRepository.save(banner);
	        }
		 
		 List<BannerResDto> boardMngrList = bannerQuerydslRepository.findAllBannerList();

		 return boardMngrList;
		 
	}


}
