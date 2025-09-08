package klj.project.repository.user.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QComment;
import klj.project.web.dto.user.board.BoardRateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public BoardRateResDto selectBoardRate(Long boardId) {
        BoardRateResDto boardRateResDto = queryFactory
                .select(Projections.fields(BoardRateResDto.class,
                        QComment.comment.tasteRate.avg().as("avgTasteRate"),
                        QComment.comment.priceRate.avg().as("avgPriceRate"),
                        QComment.comment.ingredientRate.avg().as("avgIngredientRate")
                )).from(QComment.comment)
                .where(QComment.comment.board.boardId.eq(boardId))
                .fetchOne();

        return boardRateResDto;
    }
}
