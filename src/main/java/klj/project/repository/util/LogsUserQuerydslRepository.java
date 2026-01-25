package klj.project.repository.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.file.QFiles;
import klj.project.domain.util.QLogs;
import klj.project.domain.util.QLogsUser;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.LogsResDto;
import klj.project.web.dto.user.board.BoardResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogsUserQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<LogsResDto> findAllUserLogsList (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();
        BooleanBuilder builder = new BooleanBuilder();
        if (searchText != null && !searchText.isEmpty()) {
            builder.and(QLogsUser.logsUser.ipAddress.contains(searchText));
        }

        LocalDateTime searchStartDate = null;
        if(pageReqDto.getSearchStartDate() != null){
            searchStartDate = pageReqDto.getSearchStartDate().atStartOfDay();  // 예: 2024-01-01T00:00
        }

        LocalDateTime searchEndDate = null;
        if(pageReqDto.getSearchEndDate() != null){
            searchEndDate = pageReqDto.getSearchEndDate().atTime(LocalTime.MAX);
        }

        // 날짜 조건 필터
        if (searchStartDate != null && searchEndDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.between(searchStartDate, searchEndDate));
        } else if (searchStartDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.goe(searchStartDate));
        } else if (searchEndDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.loe(searchEndDate));
        }

        List<LogsResDto> logsList = queryFactory
                .select(Projections.fields(LogsResDto.class,
                        QLogsUser.logsUser.id,
                        QLogsUser.logsUser.description,
                        QLogsUser.logsUser.ipAddress,
                        QLogsUser.logsUser.logsType,
                        QLogsUser.logsUser.url,
                        QLogsUser.logsUser.createdDate,
                        QLogsUser.logsUser.device,
                        QLogsUser.logsUser.browser
                )).from(QLogsUser.logsUser)
                .where(
                        builder
                )
                .offset((pageReqDto.getCurrentPage() - 1) * pageReqDto.getItemsPerPage())
                .limit(pageReqDto.getItemsPerPage())
                .orderBy(QLogsUser.logsUser.createdDate.desc(),QLogsUser.logsUser.id.desc())
                .fetch();

        return logsList;
    }

    public Long findAllUserLogsCount (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();
        BooleanBuilder builder = new BooleanBuilder();
        if (searchText != null && !searchText.isEmpty()) {
            builder.and(QLogsUser.logsUser.ipAddress.contains(searchText));
        }

        LocalDateTime searchStartDate = null;
        if(pageReqDto.getSearchStartDate() != null){
            searchStartDate = pageReqDto.getSearchStartDate().atStartOfDay();
        }

        LocalDateTime searchEndDate = null;
        if(pageReqDto.getSearchEndDate() != null){
            searchEndDate = pageReqDto.getSearchEndDate().atTime(LocalTime.MAX);
        }

        // 날짜 조건 필터
        if (searchStartDate != null && searchEndDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.between(searchStartDate, searchEndDate));
        } else if (searchStartDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.goe(searchStartDate));
        } else if (searchEndDate != null) {
            builder.and(QLogsUser.logsUser.createdDate.loe(searchEndDate));
        }

        Long logsListCount = queryFactory
                .select(
                        QLogsUser.logsUser.count()
                ).from(QLogsUser.logsUser)
                .where(
                        builder
                )
                .fetchOne();

        return logsListCount;
    }



    public List<BoardMngrResDto> findAllUserBoardLogsList (Long userId){

        QFiles filesSubQuery = new QFiles("filesSubQuery");  // 별도 alias 생성

        List<BoardMngrResDto> logsList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardName,
                        QBoard.board.boardId,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.createDate,
                        QBoard.board.brandCodeId,
                        QBoard.board.fileGroupId,
                        QFiles.files.filePath.as("imgUrl")
                ))
                .from(QLogsUser.logsUser)
                .leftJoin(QBoard.board)
                .on(QBoard.board.boardId.eq(
                        QLogsUser.logsUser.boardId
                ))
                .leftJoin(QFiles.files)
                .on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId)
                        .and(QFiles.files.id.eq(
                                JPAExpressions.select(filesSubQuery.id.min())
                                        .from(filesSubQuery)
                                        .where(filesSubQuery.fileGroup.id.eq(QBoard.board.fileGroupId))
                        ))
                )
                .where(
                        QLogsUser.logsUser.userId.eq(userId),
                        QLogsUser.logsUser.boardId.isNotNull(),
                        QLogsUser.logsUser.boardId.ne(0L)
                )
                .orderBy(QLogsUser.logsUser.createdDate.desc(), QLogsUser.logsUser.id.desc())
                .limit(5)
                .fetch();

        return logsList;
    }


}
