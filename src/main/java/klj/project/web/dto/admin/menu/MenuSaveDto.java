package klj.project.web.dto.admin.menu;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuSaveDto {
    private List<MenuReqDto> insertList;
    private List<MenuReqDto> updateList;
    private List<Long> deleteIdList;
}
