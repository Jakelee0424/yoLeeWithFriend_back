package klj.project.web.dto.user.board;

import klj.project.web.dto.admin.board.NuinfoResDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUserResDto {
    private Long boardId;
    private String boardName;
    private String brandCodeId;
    private String useYn;
    private String delYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private String boardCategoryCodeId;
    private String nuinfoId;
    private String imgUrl;
    private Long readCnt;
    private String fileGroupId;

    @Nullable
    private List<NuinfoResDto> nuinfoResDtoList;

}
