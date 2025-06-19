package klj.project.service.admin.baord;

import klj.project.domain.board.Board;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.repository.board.BoardRepository;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardMngrService {
    private final BoardRepository boardRepository;
    private final BoardQuerydslRepository boardQuerydslRepository;

    public List<BoardMngrResDto> findBoardMngrList(){
        List<BoardMngrResDto> boardMngrList = boardQuerydslRepository.findAllBoardMngrList();
        return boardMngrList;
    }

    public BoardMngrResDto findBoardById(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. ID: " + boardId));
        return board.toResponseDto();
    }

}
