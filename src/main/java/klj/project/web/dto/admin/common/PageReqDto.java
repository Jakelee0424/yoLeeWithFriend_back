package klj.project.web.dto.admin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageReqDto {

    private Long itemsPerPage;
    private Long pageBlockSize;
    private Long currentPage;
}
