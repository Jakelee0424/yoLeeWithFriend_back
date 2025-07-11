package klj.project.web.dto.admin.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveDto {

    private BoardMngrReqDto boardMngrReqDto;
    private List<NuinfoReqDto> nuinfoReqDtoList;
    private MultipartFile[] multipartFile;
}
