package klj.project.web.dto.user.board;

import klj.project.domain.board.Comment;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCountResDto {
    private Long totalCount;
    private Long proteinCount;
    private Long bcaaCount;
    private Long bosterCount;
}
