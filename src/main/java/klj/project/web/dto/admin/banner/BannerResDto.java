package klj.project.web.dto.admin.banner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerResDto {
	
    private Long bannerId;
    private String bannerName;
    private LocalDateTime dueDate;
    private int validDays;
    private int level;
    
}
