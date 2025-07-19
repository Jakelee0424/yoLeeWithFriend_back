package klj.project.repository.menu;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.web.dto.admin.menu.MenuResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static klj.project.domain.menu.QMenu.menu;

@Repository
@RequiredArgsConstructor
public class MenuQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<MenuResDto> getMenuList() {
        List<MenuResDto> menuResDtos = queryFactory
                .select(Projections.fields(MenuResDto.class,
                       menu.menuNo,
                       menu.upperMenuNo,
                       menu.menuNm,
                       menu.url,
                       menu.componentFileNm,
                       menu.ord,
                       menu.useYn
                ))
                .from(menu)
                .where(menu.useYn.eq("Y"))
                .orderBy(menu.upperMenuNo.asc().nullsFirst(), menu.ord.asc())
                .fetch();

        return menuResDtos;
    }
}
