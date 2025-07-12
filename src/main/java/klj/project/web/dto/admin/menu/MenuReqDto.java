// ✅ MenuReqDto.java
package klj.project.web.dto.admin.menu;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuReqDto {
    private String id;             // 프론트에서 부여한 임시 ID
    private Long menuNo;          // 실제 DB ID (있으면 수정, 없으면 신규)
    private Long upperMenuNo;   // 프론트에서 보낸 상위 ID (임시 or 실제)
    private String menuNm;
    private String url;
    private Integer ord;
    private String useYn;
}
