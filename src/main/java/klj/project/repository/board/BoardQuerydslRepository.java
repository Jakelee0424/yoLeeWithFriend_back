package klj.project.repository.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.board.QBoard;
import klj.project.domain.board.QComment;
import klj.project.domain.board.QNuinfo;
import klj.project.domain.code.QCode;
import klj.project.domain.file.QFiles;
import klj.project.web.dto.admin.board.BoardMngrReqDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.board.NuinfoResDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.user.board.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        builder.and(QComment.comment.delYn.eq("N"));



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
                .leftJoin(QComment.comment).on(QBoard.board.boardId.eq(QComment.comment.board.boardId))
                .limit(5)
                .groupBy(
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath,
                        QBoard.board.readCnt
                )
                .orderBy(QComment.comment.ingredientRate.add(QComment.comment.priceRate)
                        .add(QComment.comment.tasteRate).divide(3).avg().desc() , QBoard.board.boardId.desc())
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

    public List<BoardUserResDto> findAllBoardRandomList (String type, List<BoardMngrReqDto> boardList, int clickCnt){
        List<Long> excludeIds = boardList.stream()
                .map(BoardMngrReqDto::getBoardId)
                .collect(Collectors.toList());


        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.delYn.eq("N")); // 기본 조건
        builder.and(QBoard.board.boardCategoryCodeId.eq(type));
        builder.and(QBoard.board.boardId.notIn(excludeIds));
        int pageSize = 5;  // 한 번에 늘어나는 개수
        long limit = (clickCnt + 1) * pageSize;

        List<Long> boardIds = queryFactory
                .select(QBoard.board.boardId)
                .from(QBoard.board)
                .where(builder)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(limit)  // 게시글 기준 5개
                .fetch();

        // 2. BoardId 기준으로 Nuinfo와 Files 조회
        List<Tuple> tuples = queryFactory
                .select(
                        QBoard.board.boardId,
                        QBoard.board.brandCodeId,
                        QBoard.board.boardName,
                        QBoard.board.boardCategoryCodeId,
                        QBoard.board.useYn,
                        QBoard.board.createDate,
                        QBoard.board.modifyDate,
                        QBoard.board.nuinfoId,
                        QFiles.files.filePath,
                        QNuinfo.nuinfo.sn,
                        QNuinfo.nuinfo.value,
                        QNuinfo.nuinfo.boardId,
                        QNuinfo.nuinfo.codeId
                )
                .from(QBoard.board)
                .leftJoin(QFiles.files).on(QFiles.files.fileGroup.id.eq(QBoard.board.fileGroupId))
                .leftJoin(QNuinfo.nuinfo).on(QNuinfo.nuinfo.boardId.eq(QBoard.board.boardId))
                .join(QCode.code).on(QBoard.board.brandCodeId.eq(QCode.code.id))
                .where(QBoard.board.boardId.in(boardIds))
                .fetch();

        Map<Long, BoardUserResDto> boardMap = new LinkedHashMap<>();
        for (Tuple t : tuples) {
            Long boardId = t.get(QBoard.board.boardId);

            BoardUserResDto boardDto = boardMap.computeIfAbsent(boardId, id -> {
                BoardUserResDto dto = new BoardUserResDto();
                dto.setBoardId(id);
                dto.setBrandCodeId(t.get(QBoard.board.brandCodeId));
                dto.setBoardName(t.get(QBoard.board.boardName));
                dto.setBoardCategoryCodeId(t.get(QBoard.board.boardCategoryCodeId));
                dto.setUseYn(t.get(QBoard.board.useYn));
                dto.setCreatedDate(t.get(QBoard.board.createDate));
                dto.setModifyDate(t.get(QBoard.board.modifyDate));
                dto.setNuinfoId(t.get(QBoard.board.nuinfoId));
                dto.setImgUrl(t.get(QFiles.files.filePath));
                dto.setNuinfoResDtoList(new ArrayList<>());
                return dto;
            });

            // Nuinfo 리스트 추가 (최대 4개)
            if (t.get(QNuinfo.nuinfo.sn) != null && boardDto.getNuinfoResDtoList().size() < 4) {
                NuinfoResDto nuinfoDto = new NuinfoResDto();
                nuinfoDto.setSn(t.get(QNuinfo.nuinfo.sn));
                nuinfoDto.setValue(t.get(QNuinfo.nuinfo.value));
                nuinfoDto.setBoardId(t.get(QNuinfo.nuinfo.boardId));
                nuinfoDto.setCodeId(t.get(QNuinfo.nuinfo.codeId));
                boardDto.getNuinfoResDtoList().add(nuinfoDto);
            }
        }

        return new ArrayList<>(boardMap.values());
    }
}
