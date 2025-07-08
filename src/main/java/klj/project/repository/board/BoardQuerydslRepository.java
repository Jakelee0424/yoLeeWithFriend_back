package klj.project.repository.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.code.QCode;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.QFileGroup;
import klj.project.domain.file.QFiles;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<BoardMngrResDto> findAllBoardMngrList (PageDto pageDto){
        //List<BoardMngrResDto> boardMngrList = new ArrayList<>();
        String searchKeyword = pageDto.getSearchField();
        String searchText = pageDto.getSearchText();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건

        if (searchText != null && !searchText.isEmpty()) {
            switch (searchKeyword) {
                case "searchFiled1":
                    builder.and(QCode.code.name.contains(searchText));
                    break;
                case "searchFiled2":
                    builder.and(QBoard.board.boardName.contains(searchText));
                    break;
                case "all":
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(QCode.code.name.contains(searchText));
                    orBuilder.or(QBoard.board.boardName.contains(searchText));
                    builder.and(orBuilder);
                    break;
                // 필요 시 case 추가
                default:
                    break;
            }
        }


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
                .where(
                        builder
                )
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .join(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .fetch();

        return boardMngrList;
    }


}
