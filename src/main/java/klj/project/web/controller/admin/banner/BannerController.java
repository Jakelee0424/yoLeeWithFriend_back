package klj.project.web.controller.admin.banner;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import klj.project.domain.admin.admin.Admin;
import klj.project.service.admin.admin.AdminService;
import klj.project.service.admin.banner.BannerService;
import klj.project.service.admin.baord.BoardMngrService;
import klj.project.web.controller.admin.admin.AdminController;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.banner.BannerLevelReqDto;
import klj.project.web.dto.admin.banner.BannerReqDto;
import klj.project.web.dto.admin.banner.BannerResDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.board.BoardSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BannerController {
	
	private final BannerService bannerService;
	
    @GetMapping(path = "/bannerMngr/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> getBannerList() {

        try {
        	List<BannerResDto> bannerList = bannerService.getBannerList();
        	
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
    
    @GetMapping(path = "/bannerMngr/expired", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> getExpiredBannerList() {

        try {
        	List<BannerResDto> bannerList = bannerService.getExpiredBannerList();
        	
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
    
    @DeleteMapping(path = "/bannerMngr/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> deleteBannerById(@RequestParam("ids") List<Long> bannerIds) {

        try {
            List<BannerResDto> bannerList = bannerService.deleteBannerById(bannerIds);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }
    
    @PostMapping(path = "/bannerMngr/restore", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> restoreBannerById(@RequestBody List<Long> bannerIds) {

        try {
            List<BannerResDto> bannerList = bannerService.restoreBannerById(bannerIds);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }
    
    @PostMapping(path = "/bannerMngr/level", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> bannerLevelChange(@RequestBody List<BannerLevelReqDto> bannerLevelReqDto) {

        try {
            List<BannerResDto> bannerResList = bannerService.bannerLevelChange(bannerLevelReqDto);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerResList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }
    
    @PostMapping(path = "/bannerMngr/insert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KljResponse<List<BannerResDto>> insertBanner(@RequestParam("data") String data,
    													@RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {

        try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.registerModule(new JavaTimeModule());
        	mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        	BannerReqDto bannerReqDto = mapper.readValue(data, BannerReqDto.class);
        	
            List<BannerResDto> bannerList = bannerService.insertBanner(bannerReqDto, multipartFile);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
    
    @PostMapping(path = "/bannerMngr/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BannerResDto>> updateBanner(@RequestParam("data") String data,
    													@RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {

        try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.registerModule(new JavaTimeModule());
        	mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        	BannerLevelReqDto bannerLevelReqDto = mapper.readValue(data, BannerLevelReqDto.class);
        	
            List<BannerResDto> bannerList = bannerService.updateBanner(bannerLevelReqDto, multipartFile);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(bannerList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }
    
}
