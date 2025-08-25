package klj.project.web.dto.admin.board;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NuinfoResDto {

    private Long sn;
    private String codeId;
    private Long boardId;
    private String value;
    private String name;

}
