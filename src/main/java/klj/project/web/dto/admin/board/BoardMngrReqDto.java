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
public class BoardMngrReqDto {
    private Long boardId;
    private String boardName;
    private String brandCodeId;
    private String useYn;
    private String delYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private String boardCategoryCodeId;

}
