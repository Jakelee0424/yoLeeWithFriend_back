package klj.project.service.admin.banner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import klj.project.domain.banner.Banner;
import klj.project.domain.board.Board;
import klj.project.repository.banner.BannerQuerydslRepository;
import klj.project.web.dto.admin.banner.BannerLevelReqDto;
import klj.project.web.dto.admin.banner.BannerReqDto;
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

	public List<BannerResDto> insertBanner(BannerReqDto bannerReqDto) {
		int nextLevel = bannerQuerydslRepository.findMaxLevel() + 1;
		String bannerName = bannerReqDto.getBannerName();
		LocalDateTime createdAt = bannerReqDto.getCreatedAt();
		int validDays = bannerReqDto.getValidDays();
		
		Banner banner = Banner.insertBanner(bannerName, createdAt, validDays, nextLevel);
		banner = bannerRepository.save(banner);
		
		List<BannerResDto> boardMngrList = bannerQuerydslRepository.findAllBannerList();
		return boardMngrList;
	}

	public List<BannerResDto> bannerLevelChange(List<BannerLevelReqDto> bannerLevelReqDto) {
	    for (BannerLevelReqDto dto : bannerLevelReqDto) {
	        Banner banner = bannerRepository.findById(dto.getBannerId()).orElseThrow(() -> new RuntimeException("배너를 찾을 수 없습니다: " + dto.getBannerId()));
	        
	        banner.changeLevel(dto.getLevel());
	        bannerRepository.save(banner);
	    }
	    
	    return bannerQuerydslRepository.findAllBannerList();
	}

	public List<BannerResDto> updateBanner(BannerLevelReqDto bannerLevelReqDto) {
		Long bannerId = bannerLevelReqDto.getBannerId();
		String bannerName = bannerLevelReqDto.getBannerName();
		LocalDateTime createdAt = bannerLevelReqDto.getCreatedAt();
		int validDays = bannerLevelReqDto.getValidDays();
		
		Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new RuntimeException("배너를 찾을 수 없습니다: " + bannerId));
		banner.updateBanner(bannerName, createdAt, validDays);
		
		banner = bannerRepository.save(banner);
		
		List<BannerResDto> boardMngrList = bannerQuerydslRepository.findAllBannerList();
		return boardMngrList;
	}

	public List<BannerResDto> restoreBannerById(List<Long> bannerIds) {
		for(int i=0; i< bannerIds.size(); i++){
            Long bannerId = bannerIds.get(i);
            Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new IllegalArgumentException("해당 배너를 찾을 수 없습니다. ID: " + bannerId));
            banner.restoreBanner();
            bannerRepository.save(banner);
	    }
		 
		 List<BannerResDto> boardMngrList = bannerQuerydslRepository.findExpiredBannerList();
	
		 return boardMngrList;
	}


}
