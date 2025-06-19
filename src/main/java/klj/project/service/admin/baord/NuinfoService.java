package klj.project.service.admin.baord;

import klj.project.domain.board.Board;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.repository.board.BoardRepository;
import klj.project.repository.board.NuinfoQuerydslRepository;
import klj.project.repository.board.NuinfoRepository;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.board.NuinfoResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class NuinfoService {
    private final NuinfoRepository nuinfoRepository;
    private final NuinfoQuerydslRepository nuinfoQuerydslRepository;

    public List<NuinfoResDto> findByBoardIdForNuinfoList(Long boardId){
        List<NuinfoResDto> nuinfoList = nuinfoQuerydslRepository.findByBoardIdForNuinfoList(boardId);

        return nuinfoList;
    }

}
