package klj.project.web.dto.admin.banner;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerReqDto {
	
    private String bannerName;
    private LocalDateTime createdAt;
    private Integer validDays;
    private MultipartFile multipartFile;
    
}
