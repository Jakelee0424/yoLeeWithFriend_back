package klj.project.domain.menu;

import jakarta.persistence.*;
import klj.project.web.dto.admin.menu.MenuResDto;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_no")
	private Long menuNo;

	@Column(name = "upper_menu_no")
	private Long upperMenuNo;

	@Column(name = "menu_nm", nullable = false, length = 100)
	private String menuNm;

	@Column(name = "url", length = 255)
	private String url;

	@Column(name = "component_file_nm")
	private String componentFileNm;

	@Column(name = "ord", nullable = false)
	private Integer ord;

	@Column(name = "use_yn", nullable = false, length = 1)
	private String useYn;

	@Builder
	public Menu(Long menuNo, Long upperMenuNo, String menuNm, String url, String componentFileNm, Integer ord, String useYn) {
		this.menuNo = menuNo;
		this.upperMenuNo = upperMenuNo;
		this.menuNm = menuNm;
		this.url = url;
		this.componentFileNm = componentFileNm;
		this.ord = ord;
		this.useYn = useYn;
	}

	// DTO 변환
	public MenuResDto toMenuResDto() {
		return MenuResDto.builder()
				.menuNo(this.menuNo)
				.upperMenuNo(this.upperMenuNo)
				.menuNm(this.menuNm)
				.url(this.url)
				.componentFileNm(this.componentFileNm)
				.ord(this.ord)
				.useYn(this.useYn)
				.children(new ArrayList<>())
				.build();
	}
}
