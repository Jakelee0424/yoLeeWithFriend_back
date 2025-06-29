package klj.project.repository.util;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.file.QFiles;
import klj.project.domain.util.QLogs;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.util.LogsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogsQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<LogsResDto> findAllLogsList (){
        List<LogsResDto> logsList = queryFactory
                .select(Projections.fields(LogsResDto.class,
                        QLogs.logs.id,
                        QLogs.logs.description,
                        QLogs.logs.ipAddress,
                        QLogs.logs.logsType,
                        QLogs.logs.url,
                        QLogs.logs.createdDate
                )).from(QLogs.logs)
                .fetch();

        return logsList;
    }


}
