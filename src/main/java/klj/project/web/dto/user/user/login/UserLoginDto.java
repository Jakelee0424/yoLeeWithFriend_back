package klj.project.web.dto.user.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {

    private Long id;
    private String nickName;
    private String profilePath;
}
