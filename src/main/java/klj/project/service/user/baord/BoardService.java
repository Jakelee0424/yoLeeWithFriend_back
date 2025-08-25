package klj.project.service.user.baord;


import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.board.BoardReqDto;
import klj.project.web.dto.user.board.BoardResDto;
import klj.project.web.dto.user.board.BrandResDto;
import klj.project.web.dto.user.board.NutritionResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {
    private final BoardQuerydslRepository boardQuerydslRepository;

    public List<BoardMngrResDto> findBoardMainList(String type){
        List<BoardMngrResDto> boardMngrList = new ArrayList<>();
        if(type.equals("1")){
            boardMngrList = boardQuerydslRepository.findAllBoardMainList();
        }else{
            boardMngrList = boardQuerydslRepository.findAllBoardMain2List();
        }

        return boardMngrList;
    }

	public List<BrandResDto> getAllBrandList() {
		
		List<BrandResDto> brandList = boardQuerydslRepository.getAllBrandList();
		
		return brandList;
	}

	public List<BoardResDto> findBoardList(BoardReqDto boardReqDto) {
		List<BoardResDto> boardfindList = boardQuerydslRepository.findBoardList(boardReqDto);
		
		return boardfindList;
	}

	public List<NutritionResDto> getNutriInfo(Long boardId) {
		List<NutritionResDto> nutriInfo = boardQuerydslRepository.getNutriInfo(boardId);
		
		return nutriInfo;
	}
}
