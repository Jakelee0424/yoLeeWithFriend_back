package klj.project.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;


import klj.project.domain.board.QNuinfo;
import klj.project.web.dto.admin.board.NuinfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NuinfoQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<NuinfoResDto> findByBoardIdForNuinfoList (Long boardId){
        //List<NuinfoResDto> nuinfoList = new ArrayList<>();
        List<NuinfoResDto> nuinfoList = queryFactory
                .select(Projections.fields(NuinfoResDto.class,
                        QNuinfo.nuinfo.sn,
                        QNuinfo.nuinfo.boardId,
                        QNuinfo.nuinfo.codeId,
                        QNuinfo.nuinfo.value
                )).from(QNuinfo.nuinfo)
                .where(QNuinfo.nuinfo.boardId.eq(boardId))
                .fetch();

        return nuinfoList;
    }


}
