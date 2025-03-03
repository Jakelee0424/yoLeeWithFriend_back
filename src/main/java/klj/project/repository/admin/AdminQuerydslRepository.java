package klj.project.repository.admin;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.admin.Admin;
import klj.project.domain.admin.QAdmin;
import klj.project.domain.user.user.OauthType;
import klj.project.domain.user.user.QUser;
import klj.project.domain.user.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Admin findByAdminIdAndPassWord (String adminId, String passWord){

        Admin admin = queryFactory
                .select(Projections.fields(Admin.class,
                        QAdmin.admin.id,
                        QAdmin.admin.authority,
                        QAdmin.admin.name,
                        QAdmin.admin.sn
                )).from(QAdmin.admin)
                .where(QAdmin.admin.id.eq(adminId),QAdmin.admin.passWord.eq(passWord))
                .fetchOne();

        return admin;
    }



}
