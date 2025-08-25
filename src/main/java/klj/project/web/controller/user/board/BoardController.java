package klj.project.web.controller.user.board;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.service.admin.baord.BoardMngrService;
import klj.project.service.admin.baord.NuinfoService;
import klj.project.service.user.baord.BoardService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.*;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.user.board.BoardReqDto;
import klj.project.web.dto.user.board.BoardResDto;
import klj.project.web.dto.user.board.BrandResDto;
import klj.project.web.dto.user.board.NutritionResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    // 게시물 관리 서비스
    private final BoardService boardService;


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
}
