package klj.project.web.dto.user.board;

import klj.project.domain.board.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentResDto {
    private Long count;
    private Long commentId;


}
