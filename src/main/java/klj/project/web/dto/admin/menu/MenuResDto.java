package klj.project.web.dto.admin.menu;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResDto {
    private Long menuNo;
    private Long upperMenuNo;
    private String menuNm;
    private String url;
    private String componentFileNm;
    private Integer ord;
    private String useYn;

    @Builder.Default
    private List<MenuResDto> children = new ArrayList<>();
}