package klj.project.web.dto.admin.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSaveDto {

    private Long id;
    private String adminId;
    private String passWord;
    private String name;
}
