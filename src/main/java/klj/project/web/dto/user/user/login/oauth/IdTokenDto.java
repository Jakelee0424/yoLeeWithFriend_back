package klj.project.web.dto.user.user.login.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IdTokenDto {
    private String aud;
    private String sub;
    private String auth_time;
    private String iss;
    private String exp;
    private String iat;

}
