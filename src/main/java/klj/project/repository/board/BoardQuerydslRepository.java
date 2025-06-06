package klj.project.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
         List<BoardMngrResDto> boardMngrList = new ArrayList<>();
//
//        List<BoardMngrResDto> boardMngrList = queryFactory
//                .select(Projections.fields(BoardMngrResDto.class,
//                        QAdmin.admin.authority,
//                        QAdmin.admin.name,
//                        QAdmin.admin.sn
//                )).from(QAdmin.admin)
//                .where(QAdmin.admin.id.eq(adminId),QAdmin.admin.passWord.eq(passWord))
//                .fetch();

        return boardMngrList;
    }


}
