package klj.project.web.dto.admin.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardMngrDetailResDto {

    private BoardMngrResDto boardMngrResDto;
    private List<NuinfoResDto> nuinfoResDtoList;

}
