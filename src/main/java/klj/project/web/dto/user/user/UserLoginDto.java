package klj.project.web.dto.user.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    private Long id;
    private String nickName;
    private String profilePath;
}
