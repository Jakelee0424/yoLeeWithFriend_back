package klj.project.web.dto.admin.banner;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerLevelReqDto {
	
    private Long bannerId;
    private Integer level;
}
