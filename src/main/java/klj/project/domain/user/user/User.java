package klj.project.domain.user.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @Builder
    public User(String oauthId, OauthType oauthType, Authority authority, String nickName, UserStatus status) {
        this.oauthId = oauthId;
        this.oauthType = oauthType;
        this.authority = authority;
        this.nickName = nickName;
        this.status = status;
    }

    public static  User createUser (String oauthId, OauthType oauthType, Authority authority, String nickName, UserStatus status){
        return User.builder()
                .oauthId(oauthId)
                .oauthType(oauthType)
                .authority(authority)
                .nickName(nickName)
                .status(status)
                .build();
    }

    public User changeNickName (String nickName){
        this.nickName = nickName;
        return this;
    }

    public User changeUserStatus (UserStatus status){
        this.status = status;
        return this;
    }


}
