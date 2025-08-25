package klj.project.repository.board;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.board.QNuinfo;
import klj.project.domain.code.QCode;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.QFileGroup;
import klj.project.domain.file.QFiles;
import klj.project.domain.util.QLogs;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.user.board.BoardReqDto;
import klj.project.web.dto.user.board.BoardResDto;
import klj.project.web.dto.user.board.BrandResDto;
import klj.project.web.dto.user.board.NutritionResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardQuerydslRepository {

    private final JPAQueryFactory queryFactory;
    public List<BoardMngrResDto> findAllBoardMngrList (PageReqDto pageReqDto){
        //List<BoardMngrResDto> boardMngrList = new ArrayList<>();
        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건
        if(!pageReqDto.getType().equals("all")) {
        	builder.and(QBoard.board.boardCategoryCodeId.eq(pageReqDto.getType()));
        }

        if (searchText != null && !searchText.isEmpty()) {
            switch (searchKeyword) {
                case "searchField1":
                    builder.and(QCode.code.name.contains(searchText));
                    break;
                case "searchField2":
                    builder.and(QBoard.board.boardName.contains(searchText));
                    break;
                case "all":
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(QCode.code.name.contains(searchText));
                    orBuilder.or(QBoard.board.boardName.contains(searchText));
                    builder.and(orBuilder);
                    break;
                // 필요 시 case 추가
                default:
                    break;
            }
        }


        List<BoardMngrResDto> boardMngrList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath.as("imgUrl")
                )).from(QBoard.board)
                .where(
                        builder
                )
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .leftJoin(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .offset((pageReqDto.getCurrentPage() - 1) * pageReqDto.getItemsPerPage())
                .limit(pageReqDto.getItemsPerPage())
                .orderBy(QBoard.board.boardId.desc())
                .fetch();

        return boardMngrList;
    }

    public Long findAllBoardMngrListCount (PageReqDto pageReqDto){

        String searchKeyword = pageReqDto.getSearchField();
        String searchText = pageReqDto.getSearchText();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건
        builder.and(QBoard.board.boardCategoryCodeId.eq(pageReqDto.getType()));

        if (searchText != null && !searchText.isEmpty()) {
            switch (searchKeyword) {
                case "searchField1":
                    builder.and(QCode.code.name.contains(searchText));
                    break;
                case "searchField2":
                    builder.and(QBoard.board.boardName.contains(searchText));
                    break;
                case "all":
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(QCode.code.name.contains(searchText));
                    orBuilder.or(QBoard.board.boardName.contains(searchText));
                    builder.and(orBuilder);
                    break;
                // 필요 시 case 추가
                default:
                    break;
            }
        }


        Long logsListCount = queryFactory
                .select(
                        QBoard.board.count()
                ).from(QBoard.board)
                .leftJoin(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .where(
                        builder
                )
                .fetchOne();

        return logsListCount;
    }

    public List<BoardMngrResDto> findAllBoardMainList (){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건


        List<BoardMngrResDto> boardMngrList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath.as("imgUrl"),
                        QBoard.board.readCnt
                )).from(QBoard.board)
                .where(
                        builder
                )
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .leftJoin(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .limit(5)
                .orderBy(QBoard.board.readCnt.desc() , QBoard.board.boardId.desc())
                .fetch();

        return boardMngrList;
    }

    public List<BoardMngrResDto> findAllBoardMain2List (){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건

        List<BoardMngrResDto> boardMngrList = queryFactory
                .select(Projections.fields(BoardMngrResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath.as("imgUrl")
                )).from(QBoard.board)
                .where(
                        builder
                )
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .join(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .limit(5)
                .orderBy(QBoard.board.boardId.desc())
                .fetch();

        return boardMngrList;
    }

	public List<BrandResDto> getAllBrandList() {
		
		List<BrandResDto> brandList = queryFactory
                .select(Projections.fields(BrandResDto.class,
                        QCode.code.id,
                        QCode.code.name
                )).from(QCode.code)
                .where(
                		QCode.code.codeParent.id.eq("boardBrand")
                )
                .fetch();
		
		return brandList;
	}

	public List<BoardResDto> findBoardList(BoardReqDto boardReqDto) {

		String type = boardReqDto.getType();
		String brandId = boardReqDto.getBrandId();
		String queryParam = boardReqDto.getQueryParam();
		
		BooleanBuilder builder = new BooleanBuilder();
		
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건
        
        if(!type.equals("all")) {
        	builder.and(QBoard.board.boardCategoryCodeId.eq(type));
        }
        
        if(!brandId.equals("boardBand00")) {
        	builder.and(QBoard.board.brandCodeId.eq(brandId));
        }
        
        if(!queryParam.equals("")) {
        	builder.and(QBoard.board.boardName.contains(queryParam));
        }
		
		List<BoardResDto> boardList = queryFactory
                .select(Projections.fields(BoardResDto.class,
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath.as("imgUrl")
                )).from(QBoard.board)
                .where(
                        builder
                )
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .leftJoin(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .fetch();

        return boardList;
	}

	public List<NutritionResDto> getNutriInfo(Long boardId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(QCode.code.codeParent.id.eq("nutritionInformation01")); // 기본 조건
		builder.and(QNuinfo.nuinfo.boardId.eq(boardId));
		
		List<NutritionResDto> nutriInfo = queryFactory
			    .select(Projections.fields(NutritionResDto.class,
			            QCode.code.name.as("name"),
			            QNuinfo.nuinfo.value.as("value")
			    ))
			    .from(QCode.code)
			    .leftJoin(QNuinfo.nuinfo)
			    .on(QNuinfo.nuinfo.codeId.eq(QCode.code.id)) 
			    .where(
			    		builder
			    		)
			    .fetch();
		
		return nutriInfo;
	}
	

}
