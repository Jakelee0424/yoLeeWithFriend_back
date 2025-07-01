package klj.project.service.admin.banner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import klj.project.domain.banner.Banner;
import klj.project.domain.board.Board;
import klj.project.domain.file.FileCategory;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.repository.banner.BannerQuerydslRepository;
import klj.project.repository.file.FileGroupRepository;
import klj.project.repository.file.FileRepository;
import klj.project.util.FileManageUtil;
import klj.project.web.dto.admin.banner.BannerLevelReqDto;
import klj.project.web.dto.admin.banner.BannerCreateReqDto;
import klj.project.web.dto.admin.banner.BannerResDto;
import klj.project.web.dto.admin.banner.BannerUpdateReqDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BannerService {

	private final BannerRepository bannerRepository;
	private final BannerQuerydslRepository bannerQuerydslRepository;
    private final FileGroupRepository fileGroupRepository;
    private final FileRepository fileRepository;
	
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

	public List<BannerResDto> insertBanner(BannerCreateReqDto bannerCreateReqDto, MultipartFile multipartFile) throws Exception {
		Long fileGroupId = null;
		
		int nextLevel = bannerQuerydslRepository.findMaxLevel() + 1;
		String bannerName = bannerCreateReqDto.getBannerName();
		LocalDateTime createdAt = bannerCreateReqDto.getCreatedAt();
		int validDays = bannerCreateReqDto.getValidDays();
		
        if(multipartFile !=null){
            LocalDateTime localDateTime = LocalDateTime.now();
            FileGroup fileGroup = FileGroup.createFileGroup(FileCategory.img, "배너이미지", localDateTime);
            fileGroup = fileGroupRepository.save(fileGroup);
            fileGroupId = fileGroup.getId();
            MultipartFile[] multipartFiles = new MultipartFile[1];
            multipartFiles[0] = multipartFile;
            List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup, FileType.jpg);
            fileRepository.saveAll(filesInsertList);
        }
		
		Banner banner = Banner.insertBanner(bannerName, createdAt, validDays, nextLevel, fileGroupId);
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

	public List<BannerResDto> updateBanner(BannerUpdateReqDto bannerUpdateReqDto, MultipartFile multipartFile) throws Exception {
		Long fileGroupId = null;
		
		Long bannerId = bannerUpdateReqDto.getBannerId();
		String bannerName = bannerUpdateReqDto.getBannerName();
		int validDays = bannerUpdateReqDto.getValidDays();
		LocalDateTime createdAt = bannerUpdateReqDto.getCreatedAt();
		
        if(multipartFile !=null){
            LocalDateTime localDateTime = LocalDateTime.now();
            FileGroup fileGroup = FileGroup.createFileGroup(FileCategory.img, "배너이미지", localDateTime);
            fileGroup = fileGroupRepository.save(fileGroup);
            fileGroupId = fileGroup.getId();
            MultipartFile[] multipartFiles = new MultipartFile[1];
            multipartFiles[0] = multipartFile;
            List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup, FileType.jpg);
            fileRepository.saveAll(filesInsertList);
        }else {
        	fileGroupId = bannerUpdateReqDto.getFileGroupId();
        }
		
		Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new RuntimeException("배너를 찾을 수 없습니다: " + bannerId));
		banner.updateBanner(bannerName, createdAt, validDays, fileGroupId);
		
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
