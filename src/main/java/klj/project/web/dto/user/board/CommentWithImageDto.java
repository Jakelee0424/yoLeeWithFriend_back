package klj.project.web.dto.user.board;

import klj.project.domain.board.Comment;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentWithImageDto {

    // Comment 정보
    private Long commentId;
    private Long userId;
    private Long boardId;
    private String content;
    private String delYn;
    private LocalDateTime regDt;

    // Board 정보
    private String boardName;
    private String boardCategoryCodeId;
    private String brandCodeId;

    // 이미지 정보
    private String imgUrl;

    // Comment 엔티티를 받아서 매핑하는 생성자
    public CommentWithImageDto(Comment comment, String imgUrl) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getBoardId();
        this.content = comment.getContent();
        this.delYn = comment.getDelYn();
        this.regDt = comment.getRegDt();

        if (comment.getBoard() != null) {
            this.boardName = comment.getBoard().getBoardName();
            this.boardCategoryCodeId = comment.getBoard().getBoardCategoryCodeId();
            this.brandCodeId = comment.getBoard().getBrandCodeId();
        }

        this.imgUrl = imgUrl;
    }
}