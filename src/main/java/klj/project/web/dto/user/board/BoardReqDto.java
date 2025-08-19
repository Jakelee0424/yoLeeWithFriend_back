package klj.project.web.dto.user.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardReqDto {
	
	private String brandId;
	private String type;
	private String queryParam;
	private int itemsPerPage;
	private int pageBlockSize;
	private int currentPage;

}
