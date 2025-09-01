package klj.project.web.dto.user.board;

import klj.project.web.dto.admin.board.BoardMngrReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRandomReqDto {

	private String type;
	private List<BoardMngrReqDto> boardList;
	private int clickCnt;

}
