package klj.project.web.dto.user.board;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReqDto {
    private Long boardId;
    private Long userId;
    private String content;
    private Integer tasteRate;
    private Integer priceRate;
    private Integer ingredientRate;
}
