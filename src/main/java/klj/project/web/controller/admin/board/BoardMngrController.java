package klj.project.web.controller.admin.board;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.service.admin.baord.BoardMngrService;
import klj.project.service.admin.baord.NuinfoService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.*;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
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
public class BoardMngrController {

    // 게시물 관리 서비스
    private final BoardMngrService boardMngrService;
    
    // 영양정보 서비스
    private final NuinfoService nuinfoService;

    // 게시물 관리 레포
    private final BoardQuerydslRepository boardQuerydslRepository;

    @GetMapping("/boardMngr/all")
    public KljResponse<PageDto> findBoardMngrList(@ModelAttribute PageReqDto pageReqDto) {

        try {

            List<BoardMngrResDto> boardMngrList = boardMngrService.findBoardMngrList(pageReqDto);
            Long allBoardMngrListCount = boardQuerydslRepository.findAllBoardMngrListCount(pageReqDto);
            PageDto<BoardMngrResDto> boardResDtoPageDto = new PageDto<>(boardMngrList, allBoardMngrListCount);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardResDtoPageDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping(path = "/board/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<BoardMngrDetailResDto> findBoardById(@PathVariable("id") Long boardId) {

        try {

            BoardMngrResDto boardMngrResDto = boardMngrService.findBoardById(boardId);

            List<NuinfoResDto> nuinfoList = nuinfoService.findByBoardIdForNuinfoList(boardId);

            BoardMngrDetailResDto boardMngrDetailResDto = new BoardMngrDetailResDto();
            boardMngrDetailResDto.setBoardMngrResDto(boardMngrResDto);
            boardMngrDetailResDto.setNuinfoResDtoList(nuinfoList);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardMngrDetailResDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @DeleteMapping(path = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<BoardMngrResDto>> deleteBoardById(@RequestParam("ids") List<Long> boardIds) {

        try {
            List<BoardMngrResDto> boardDeleteList = boardMngrService.deleteBoardById(boardIds);


            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardDeleteList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @PostMapping(path = "/board", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KljResponse<BoardMngrDetailResDto> saveBoard( @RequestParam("data") String data,
                                                         @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            BoardSaveDto boardSaveDto = mapper.readValue(data, BoardSaveDto.class);

            BoardMngrDetailResDto boardMngrDetailResDto = new BoardMngrDetailResDto();

            // 게시판 정보 정보
            BoardMngrReqDto boardMngrReqDto = boardSaveDto.getBoardMngrReqDto();
            BoardMngrResDto boardMngrResDto = boardMngrService.saveBoard(boardMngrReqDto, multipartFile);
            boardMngrDetailResDto.setBoardMngrResDto(boardMngrResDto);

            // 영양정보 세팅
            List<NuinfoReqDto> nuinfoReqDtoList = boardSaveDto.getNuinfoReqDtoList();
            List<NuinfoResDto> nuinfoResDtoList = boardMngrService.saveNuinfo(nuinfoReqDtoList, boardMngrResDto.getBoardId());
            boardMngrDetailResDto.setNuinfoResDtoList(nuinfoResDtoList);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardMngrDetailResDto);

        }catch (NullPointerException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }catch (UnsupportedOperationException e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        } catch (JsonMappingException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        } catch (JsonProcessingException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }

    }
}
