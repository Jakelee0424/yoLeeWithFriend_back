package klj.project.repository.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.code.QCode;
import klj.project.domain.file.QFiles;
import klj.project.domain.util.QLogs;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.LogsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogsQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<LogsResDto> findAllLogsList (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();
        BooleanBuilder builder = new BooleanBuilder();
        if (searchText != null && !searchText.isEmpty()) {
            builder.and(QLogs.logs.ipAddress.contains(searchText));
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
            builder.and(QLogs.logs.createdDate.between(searchStartDate, searchEndDate));
        } else if (searchStartDate != null) {
            builder.and(QLogs.logs.createdDate.goe(searchStartDate));
        } else if (searchEndDate != null) {
            builder.and(QLogs.logs.createdDate.loe(searchEndDate));
        }

        List<LogsResDto> logsList = queryFactory
                .select(Projections.fields(LogsResDto.class,
                        QLogs.logs.id,
                        QLogs.logs.description,
                        QLogs.logs.ipAddress,
                        QLogs.logs.logsType,
                        QLogs.logs.url,
                        QLogs.logs.createdDate
                )).from(QLogs.logs)
                .where(
                        builder
                )
                .offset((pageReqDto.getCurrentPage() - 1) * pageReqDto.getItemsPerPage())
                .limit(pageReqDto.getItemsPerPage())
                .orderBy(QLogs.logs.createdDate.desc(),QLogs.logs.id.desc())
                .fetch();

        return logsList;
    }

    public Long findAllLogsCount (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();
        BooleanBuilder builder = new BooleanBuilder();
        if (searchText != null && !searchText.isEmpty()) {
            builder.and(QLogs.logs.ipAddress.contains(searchText));
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
            builder.and(QLogs.logs.createdDate.between(searchStartDate, searchEndDate));
        } else if (searchStartDate != null) {
            builder.and(QLogs.logs.createdDate.goe(searchStartDate));
        } else if (searchEndDate != null) {
            builder.and(QLogs.logs.createdDate.loe(searchEndDate));
        }

        Long logsListCount = queryFactory
                .select(
                        QLogs.logs.count()
                ).from(QLogs.logs)
                .where(
                        builder
                )
                .fetchOne();

        return logsListCount;
    }


}
