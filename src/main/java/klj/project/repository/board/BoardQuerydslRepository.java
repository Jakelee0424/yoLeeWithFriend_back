package klj.project.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.QFileGroup;
import klj.project.domain.file.QFiles;
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
        //List<BoardMngrResDto> boardMngrList = new ArrayList<>();
        List<BoardMngrResDto> boardMngrList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath.as("imgUrl")
                )).from(QBoard.board)
                .where(QBoard.board.delYn.eq("N"))
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .fetch();

        return boardMngrList;
    }


}
