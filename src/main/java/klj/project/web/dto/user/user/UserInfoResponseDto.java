package klj.project.web.dto.user.user;


import jakarta.persistence.*;
import klj.project.domain.user.user.Authority;
import klj.project.domain.user.user.OauthType;
import klj.project.domain.user.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private Long id;
    private String nickName;
    private String profilePath;
    private String oauthId;
    private OauthType oauthType;
    private Authority authority;
    private UserStatus status;
}
