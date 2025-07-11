package klj.project.web.dto.admin.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardMngrResDto {
    private Long boardId;
    private String boardName;
    private String boardCodeId;
    private String useYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private String boardCategoryCodeId;
}
