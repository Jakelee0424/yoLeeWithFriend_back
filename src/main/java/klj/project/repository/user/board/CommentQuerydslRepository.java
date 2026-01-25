package klj.project.repository.user.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.Comment;
import klj.project.domain.board.QBoard;
import klj.project.domain.board.QComment;
import klj.project.domain.file.QFiles;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.board.BoardRateResDto;
import klj.project.web.dto.user.board.CommentCountResDto;
import klj.project.web.dto.user.board.CommentWithImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<CommentWithImageDto> findFirst3ByUserIdAndDelYnWithImage(Long userId, String delYn) {

        QComment comment = QComment.comment;
        QBoard board = QBoard.board;
        QFiles files = QFiles.files;
        QFiles filesSubQuery = new QFiles("filesSubQuery");

        return queryFactory
                .select(Projections.fields(CommentWithImageDto.class,
                        comment.commentId,
                        comment.content,
                        comment.user.id,
                        board.boardName,
                        board.boardId,
                        files.filePath.as("imgUrl")
                ))
                .from(comment)
                .leftJoin(board).on(board.boardId.eq(comment.board.boardId))
                .leftJoin(files)
                .on(files.fileGroup.id.eq(board.fileGroupId)
                        .and(files.id.eq(
                                JPAExpressions.select(filesSubQuery.id.min())
                                        .from(filesSubQuery)
                                        .where(filesSubQuery.fileGroup.id.eq(board.fileGroupId))
                        ))
                )
                .where(
                        comment.user.id.eq(userId),
                        comment.delYn.eq(delYn)
                )
                .orderBy(comment.commentId.desc())
                .limit(3)
                .fetch();
    }
}
