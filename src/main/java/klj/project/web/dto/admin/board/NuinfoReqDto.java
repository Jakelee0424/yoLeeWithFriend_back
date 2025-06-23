package klj.project.web.dto.admin.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NuinfoReqDto {

    private Long sn;
    private String codeId;
    private Long boardId;
    private String value;

}
