package klj.project.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<BoardMngrResDto> findAllBoardMngrList (){

        List<BoardMngrResDto> boardMngrList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate
                )).from(QBoard.board)
                .where(QBoard.board.delYn.eq("N"))
                .fetch();

        return boardMngrList;
    }


}
