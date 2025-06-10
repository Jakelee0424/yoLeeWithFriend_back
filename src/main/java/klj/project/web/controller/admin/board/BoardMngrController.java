package klj.project.web.controller.admin.board;

import klj.project.service.admin.baord.BoardMngrService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardMngrController {

    // 게시물 관리 서비스
    private final BoardMngrService boardMngrService;

    @GetMapping("/boardMngr/all")
    public KljResponse<List<BoardMngrResDto>> findBoardMngrList() {

        try {

            List<BoardMngrResDto> boardMngrList = boardMngrService.findBoardMngrList();



            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardMngrList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping(path = "/board/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<BoardMngrResDto> findBoardById(@PathVariable("id") Long boardId) {

        try {

            BoardMngrResDto boardMngrResDto = boardMngrService.findBoardById(boardId);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(boardMngrResDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }
}
