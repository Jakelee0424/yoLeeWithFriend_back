package klj.project.repository.banner;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import klj.project.domain.banner.Banner;
import klj.project.domain.banner.QBanner;
import klj.project.domain.board.QBoard;
import klj.project.domain.file.QFiles;
import klj.project.domain.user.user.QUser;
import klj.project.domain.user.user.User;
import klj.project.web.dto.admin.banner.BannerReqDto;
import klj.project.web.dto.admin.banner.BannerResDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BannerQuerydslRepository {

	private final JPAQueryFactory queryFactory;
	
	public List<BannerResDto> findAllBannerList() {
		
		 List<BannerResDto> bannerList =  queryFactory
                .select(Projections.fields(BannerResDto.class,
                        QBanner.banner.bannerId,
                        QBanner.banner.bannerName,
                        QBanner.banner.validDays,
                        QBanner.banner.level,
                        QBanner.banner.createdAt,
                        QBanner.banner.delYn
                )).from(QBanner.banner)
                .where(QBanner.banner.delYn.eq("N"))
                .orderBy(QBanner.banner.level.asc())
                .fetch();
		 
		return bannerList;
	}

	public List<BannerResDto> findExpiredBannerList() {
		 List<BannerResDto> bannerList =  queryFactory
	                .select(Projections.fields(BannerResDto.class,
	                        QBanner.banner.bannerId,
	                        QBanner.banner.bannerName,
	                        QBanner.banner.validDays,
	                        QBanner.banner.level,
	                        QBanner.banner.createdAt,
	                        QBanner.banner.delYn
	                )).from(QBanner.banner)
	                .where(QBanner.banner.delYn.eq("Y"))
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
