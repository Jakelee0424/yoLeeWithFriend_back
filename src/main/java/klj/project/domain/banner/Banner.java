package klj.project.domain.banner;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import klj.project.web.dto.admin.banner.BannerResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "banner")
@NoArgsConstructor
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banner_id")
	private Long bannerId;

	@Column(name = "banner_name")
	private String bannerName;

	@Column(name = "valid_days")
	private int validDays;

	@Column(name = "level")
	private int level;

	@Column(name = "del_yn")
	private String delYn;

	@Column(name = "created_at")
	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	public Banner(Long bannerId, String bannerName, int validDays, int level, String delYn) {
		this.bannerId = bannerId;
		this.bannerName = bannerName;
		this.validDays = validDays;
		this.level = level;
		this.delYn = delYn;
	}

	public Banner deleteBanner() {
		this.delYn = "Y";
		return this;
	}

	public BannerResDto toResponseDto() {
		return new BannerResDto(
        	this.bannerId,
			this.bannerName,
			this.createdAt,
			this.validDays,
			this.level,
			this.delYn
        );
	}
}
