package klj.project.repository.banner;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import klj.project.domain.banner.Banner;
import klj.project.domain.banner.QBanner;
import klj.project.domain.board.QBoard;
import klj.project.domain.file.QFiles;
import klj.project.domain.user.user.QUser;
import klj.project.domain.user.user.User;
import klj.project.web.dto.admin.banner.BannerCreateReqDto;
import klj.project.web.dto.admin.banner.BannerResDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BannerQuerydslRepository {

	private final JPAQueryFactory queryFactory;
	
	public List<BannerResDto> findAllBannerList() {
		 LocalDateTime now = LocalDateTime.now();

		 BooleanExpression notDeletedAndValid = QBanner.banner.delYn.eq("N")
		     .and(Expressions.dateTemplate(LocalDateTime.class,
		         "TIMESTAMPADD(DAY, {1}, {0})",
		         QBanner.banner.createdAt, QBanner.banner.validDays)
		         .goe(now));
		
		 List<BannerResDto> bannerList =  queryFactory
                .select(Projections.fields(BannerResDto.class,
                        QBanner.banner.bannerId,
                        QBanner.banner.bannerName,
                        QBanner.banner.validDays,
                        QBanner.banner.level,
                        QBanner.banner.createdAt,
                        QBanner.banner.delYn,
                        QBanner.banner.fileGroupId,
                        QFiles.files.filePath.as("imgUrl"),
						QBanner.banner.url
                )).from(QBanner.banner)
                .where(notDeletedAndValid)
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBanner.banner.fileGroupId))
                .orderBy(QBanner.banner.level.asc())
                .fetch();
		 
		return bannerList;
	}

	public List<BannerResDto> findExpiredBannerList() {
		LocalDateTime now = LocalDateTime.now();
		BooleanExpression expiredBannerCondition =
			    QBanner.banner.delYn.eq("N") // 삭제되지 않은 배너
			    .and(Expressions.dateTemplate(LocalDateTime.class,
			            "TIMESTAMPADD(DAY, {1}, {0})",
			            QBanner.banner.createdAt, QBanner.banner.validDays)
			        .lt(now));
		
		List<BannerResDto> bannerList =  queryFactory
	                .select(Projections.fields(BannerResDto.class,
	                        QBanner.banner.bannerId,
	                        QBanner.banner.bannerName,
	                        QBanner.banner.validDays,
	                        QBanner.banner.level,
	                        QBanner.banner.createdAt,
	                        QBanner.banner.delYn,
	                        QBanner.banner.fileGroupId,
	                        QFiles.files.filePath.as("imgUrl")
	                )).from(QBanner.banner)
	                .where(expiredBannerCondition)
	                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBanner.banner.fileGroupId))
	                .orderBy(QBanner.banner.level.asc())
	                .fetch();
			 
		return bannerList;
	}
	
	public Integer findMaxLevel() {
	    Integer maxLevel = queryFactory
			        .select(QBanner.banner.level.max())
			        .from(QBanner.banner)
			        .where(QBanner.banner.delYn.eq("N"))
			        .fetchOne();

	    return maxLevel != null ? maxLevel : 0;
	}
	
}
