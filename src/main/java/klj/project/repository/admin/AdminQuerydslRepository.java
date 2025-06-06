package klj.project.repository.admin;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.admin.admin.Admin;
import klj.project.domain.admin.admin.QAdmin;
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
