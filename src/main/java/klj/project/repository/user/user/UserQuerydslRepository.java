package klj.project.repository.user.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.user.user.OauthType;
import klj.project.domain.user.user.QUser;
import klj.project.domain.user.user.User;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public User findByOauthIdAndOauthType (String oauthId, OauthType oauthType){

        User user =  queryFactory
                .select(Projections.fields(User.class,
                        QUser.user.id,
                        QUser.user.oauthId,
                        QUser.user.oauthType,
                        QUser.user.nickName,
                        QUser.user.authority
                )).from(QUser.user)
                .where(QUser.user.oauthId.eq(oauthId),QUser.user.oauthType.eq(oauthType))
                .fetchOne();
        return user;
    }

    public List<UserInfoResponseDto> findAllUserList (PageReqDto pageReqDto){
        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();

        BooleanBuilder builder = new BooleanBuilder();

        if (searchText != null && !searchText.isEmpty()) {
            switch (searchKeyword) {
                case "searchField1":
                    builder.and(QUser.user.nickName.contains(searchText));
                    break;
            }
        }


        List<UserInfoResponseDto> userList = queryFactory
                .select(Projections.bean(UserInfoResponseDto.class,
                        QUser.user.id,
                        QUser.user.nickName,
                        QUser.user.oauthType,
                        QUser.user.status,
                        QUser.user.authority,
                        QUser.user.oauthId
                )).from(QUser.user)
                .where(
                        builder
                )
                .offset((pageReqDto.getCurrentPage() - 1) * pageReqDto.getItemsPerPage())
                .limit(pageReqDto.getItemsPerPage())
                .orderBy(QUser.user.id.desc())
                .fetch();

        return userList;
    }

    public Long findAllUserListCount (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();

        BooleanBuilder builder = new BooleanBuilder();

        if (searchText != null && !searchText.isEmpty()) {
            switch (searchKeyword) {
                case "searchFiled1":
                    builder.and(QUser.user.nickName.contains(searchText));
                    break;
            }
        }


        Long userListCount = queryFactory
                .select(
                        QUser.user.count()
                ).from(QUser.user)
                .where(
                        builder
                )
                .fetchOne();

        return userListCount;
    }


}
