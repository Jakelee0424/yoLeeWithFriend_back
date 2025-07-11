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
public class PageDto<T>  extends SearchDto{

    private List<T> content;  // 실제 데이터 리스트
    private Long totalCount;
}
