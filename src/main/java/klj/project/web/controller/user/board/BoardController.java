package klj.project.web.controller.user.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import klj.project.domain.board.Board;
import klj.project.domain.board.Comment;
import klj.project.domain.file.Files;
import klj.project.domain.user.user.User;
import klj.project.repository.file.FileRepository;
import klj.project.repository.user.board.CommentQuerydslRepository;
import klj.project.repository.user.board.CommentRepository;
import klj.project.service.user.baord.BoardService;
import klj.project.service.user.baord.CommentService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.board.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    // 게시물 관리 서비스
    private final BoardService boardService;

    // 게시물 한줄평 서비스
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentQuerydslRepository  commentQuerydslRepository;
    private final FileRepository fileRepository;
    @PersistenceContext
    private EntityManager em;


    @GetMapping("/board/all")
    public KljResponse<List<BoardMngrResDto>> findBoardList(@RequestParam("type") String type) {

        try {

            List<BoardMngrResDto> boardMainList = boardService.findBoardMainList(type);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardMainList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/board/find")
    public KljResponse<List<BoardResDto>> findBoardList(@ModelAttribute BoardReqDto boardReqDto) {

        try {

            List<BoardResDto> boardfindList = boardService.findBoardList(boardReqDto);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardfindList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/board/brand")
    public KljResponse<List<BrandResDto>> getBrandList() {

        try {

            List<BrandResDto> boardBrandList = boardService.getAllBrandList();

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardBrandList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/board/getNutriInfo")
    public KljResponse<List<NutritionResDto>> getNutriInfo(@RequestParam("boardId") Long boardId) {

        try {

            List<NutritionResDto> nutriInfoList = boardService.getNutriInfo(boardId);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(nutriInfoList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @PostMapping("/board/randomAll")
    public KljResponse<List<BoardUserResDto>> findBoardRandomList(
            @RequestBody BoardRandomReqDto boardRandomReqDto
    ) {

        try {

            List<BoardUserResDto> boardRandomList = boardService.findBoardRandomList(boardRandomReqDto.getType(), boardRandomReqDto.getBoardList(), boardRandomReqDto.getClickCnt());

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardRandomList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @PostMapping("/board/saveBoardComment")
    public KljResponse<String> saveBoardComment(@RequestBody CommentReqDto commentReqDto) {

        try {
            // 불필요한 객체 풀로드 없이 프록시만 획득해서 연관관계를 매핑
            Board boardRef = em.getReference(Board.class, commentReqDto.getBoardId());
            User userRef = em.getReference(User.class, commentReqDto.getUserId());

            Comment comment = new Comment(
                    boardRef,
                    userRef,
                    commentReqDto.getContent().trim(),
                    commentReqDto.getTasteRate(),
                    commentReqDto.getPriceRate(),
                    commentReqDto.getIngredientRate()
            );

            commentRepository.save(comment);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith("Y");
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 등록을 실패하였습니다."))
                    .buildWith(null);

        }
    }

    @GetMapping("/board/getBoardCommentById")
    public KljResponse<List<CommentResDto>> getBoardCommentById(@RequestParam Long boardId) {
        try {
            List<Comment> commentList = commentRepository.findByBoard_BoardIdAndDelYn(boardId, "N");
            List<CommentResDto> commentResDtoList = commentList.stream()
                    .map(comment -> {
                        CommentResDto dto = CommentResDto.fromEntity(comment);
                        // imgUrl 설정
                        if (comment.getUser() != null && comment.getUser().getFileGroupId() != null) {
                            Long tempfileGroupId = comment.getUser().getFileGroupId();
                            Files files = fileRepository.findByFileGroupId(tempfileGroupId).get();
                            String imgUrl = files.getFilePath();
                            dto.setImgUrl(imgUrl);
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(commentResDtoList);
        } catch (Exception e) {
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 목록조회에 실패하였습니다."))
                    .buildWith(null);
        }
    }

    @GetMapping("/board/getBoardRateById")
    public KljResponse<BoardRateResDto> getBoardRateById(@RequestParam Long boardId) {
        try {
            BoardRateResDto boardRateResDto = commentService.getBoardRate(boardId);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardRateResDto);
        } catch (Exception e) {
            log.info("게시글 평점 조회 오류 발생");
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 평점 조회 중 오류가 발생하였습니다,"))
                    .buildWith(null);
        }
    }

    @GetMapping("/board/getBoardCommentCountByUserId")
    public KljResponse<CommentCountResDto> getBoardCommentCountByUserId(@RequestParam Long userId) {
        try {
            CommentCountResDto commentCountResDto = commentService.getCommentCount(userId);


            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(commentCountResDto);
        } catch (Exception e) {
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 숫자 조회에 실패하였습니다."))
                    .buildWith(null);
        }
    }

    @GetMapping("/board/getBoardCommentByUserId")
    public KljResponse<List<CommentResDto>> getBoardCommentByUserId(@RequestParam Long userId, @RequestParam String brandCodeId, @RequestParam(defaultValue = "0") int page) {
        try {
            Pageable pageable = PageRequest.of(page, 3);
            List<Comment> commentList = commentRepository.findByUserIdAndDelYnAndBoard_BoardCategoryCodeIdOrderByCommentIdDesc(userId, "N", brandCodeId, pageable);

            List<CommentResDto> commentResDtoList = commentList.stream()
                    .map(comment -> {
                        CommentResDto dto = CommentResDto.fromEntity(comment);
                        // imgUrl 설정
                        if (comment.getBoard() != null && comment.getBoard().getFileGroupId() != null) {
                            Long tempfileGroupId = comment.getBoard().getFileGroupId();
                            Files files = fileRepository.findByFileGroupId(tempfileGroupId).get();
                            String imgUrl = files.getFilePath();
                            dto.setImgUrl(imgUrl);
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(commentResDtoList);
        } catch (Exception e) {
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 목록조회에 실패하였습니다."))
                    .buildWith(null);
        }
    }

    @DeleteMapping("/board/deleteBoardComment")
    public KljResponse<String> deleteBoardComment(@RequestParam Long commentId) {

        try {
            // 불필요한 객체 풀로드 없이 프록시만 획득해서 연관관계를 매핑
            Comment comment = commentRepository.findById(commentId).orElseThrow();
            Comment deleteComment = comment.deleteComment();

            commentRepository.save(deleteComment);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(commentId.toString());
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 등록을 실패하였습니다."))
                    .buildWith(null);

        }
    }


    @PutMapping("/board/modifyComment")
    public KljResponse<String> modifyBoardComment(@RequestBody CommentReqDto commentReqDto) {

        try {
            // 불필요한 객체 풀로드 없이 프록시만 획득해서 연관관계를 매핑
            Long commentId = commentReqDto.getCommentId();
            Comment comment = commentRepository.findById(commentId).orElseThrow();
            Comment modifyComment = comment.modifyComment(commentReqDto.getContent(), commentReqDto.getTasteRate(), commentReqDto.getPriceRate(), commentReqDto.getIngredientRate());

            commentRepository.save(modifyComment);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith("Y");
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 등록을 실패하였습니다."))
                    .buildWith(null);

        }
    }

    @GetMapping("/board/countByUserIdAndDelYn")
    public KljResponse<BoardCommentResDto> countByUserIdAndDelYn(@RequestParam Long userId, @RequestParam Long boardId) {

        try {
            BoardCommentResDto result = new BoardCommentResDto();
            Comment comment = commentRepository.findByUserIdAndDelYnAndBoard_BoardId(userId, "N", boardId);
            Long count = commentRepository.countByUserIdAndDelYnAndBoard_BoardId(userId, "N", boardId);
            Long commentId =0L;
            if(comment !=null){
                commentId = comment.getCommentId();
            }
            result.setCount(count);
            result.setCommentId(commentId);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(result);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 등록을 실패하였습니다."))
                    .buildWith(null);

        }
    }

    @GetMapping("/board/commentbyCommentIdAndDelYn")
    public KljResponse<CommentResDto> getCommentbyCommentIdAndDelYn(@RequestParam Long commentId) {

        try {

            Comment comment = commentRepository.findByCommentIdAndDelYn(commentId, "N");
            CommentResDto dto = CommentResDto.fromEntity(comment);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(dto);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "한줄평 등록을 실패하였습니다."))
                    .buildWith(null);

        }
    }
}
