package klj.project.repository.user.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.board.QComment;
import klj.project.web.dto.user.board.BoardRateResDto;
import klj.project.web.dto.user.board.CommentCountResDto;
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

    public CommentCountResDto getCommentCount(Long userId) {

        Long proteinCount = queryFactory
                .select(QComment.comment.count())
                .from(QComment.comment)
                .leftJoin(QComment.comment.board, QBoard.board)
                .where(
                        QComment.comment.delYn.eq("N"),
                        QBoard.board.boardCategoryCodeId.eq("boardCategory01"),
                        QComment.comment.user.id.eq(userId)
                )
                .fetchOne();

        Long bcaaCount = queryFactory
                .select(QComment.comment.count())
                .from(QComment.comment)
                .leftJoin(QComment.comment.board, QBoard.board)
                .where(
                        QComment.comment.delYn.eq("N"),
                        QBoard.board.boardCategoryCodeId.eq("boardCategory02")
                )
                .fetchOne();

        Long bosterCount = queryFactory
                .select(QComment.comment.count())
                .from(QComment.comment)
                .leftJoin(QComment.comment.board, QBoard.board)
                .where(
                        QComment.comment.delYn.eq("N"),
                        QBoard.board.boardCategoryCodeId.eq("boardCategory03")
                )
                .fetchOne();

        Long totalCount = proteinCount + bcaaCount + bosterCount;

        CommentCountResDto commentCountResDto = new CommentCountResDto(totalCount, proteinCount, bcaaCount, bosterCount);

        return commentCountResDto;
    }
}
