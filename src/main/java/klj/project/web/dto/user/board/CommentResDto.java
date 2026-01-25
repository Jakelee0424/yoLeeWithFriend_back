package klj.project.web.dto.user.board;

import klj.project.domain.board.Comment;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResDto {
    private Long commentId;
    private Long boardId;
    private UserInfoResponseDto user;
    private String content;
    private Integer tasteRate;
    private Integer priceRate;
    private Integer ingredientRate;
    private BoardMngrResDto board;
    private LocalDateTime regDt;
    private String imgUrl;

    public static CommentResDto fromEntity(Comment comment) {
        return CommentResDto.builder()
                .commentId(comment.getCommentId())
                .boardId(comment.getBoard() != null ? comment.getBoard().getBoardId() : null)
                .user(comment.getUser() != null ? comment.getUser().toResponseDto() : null)
                .board(comment.getBoard() !=null ? comment.getBoard().toResponseDto() : null)
                .content(comment.getContent())
                .tasteRate(comment.getTasteRate())
                .priceRate(comment.getPriceRate())
                .ingredientRate(comment.getIngredientRate())
                .regDt(comment.getRegDt())
                .build();
    }
}
