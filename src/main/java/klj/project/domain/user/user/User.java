package klj.project.domain.user.user;

import jakarta.persistence.*;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
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

    private String name;

    private String gender;

    private String birthyear;

    private String email;

    @Column(name = "file_group_id")
    private Long fileGroupId;


    @Builder
    public User(String oauthId,
                OauthType oauthType,
                Authority authority,
                String nickName,
                UserStatus status,
                String name,
                String gender,
                String birthyear,
                String email

    ) {
        this.oauthId = oauthId;
        this.oauthType = oauthType;
        this.authority = authority;
        this.nickName = nickName;
        this.status = status;
        this.name = name;
        this.gender = gender;
        this.birthyear = birthyear;
        this.email = email;
    }

    public static  User createUser (String oauthId,
                                    OauthType oauthType,
                                    Authority authority,
                                    String nickName,
                                    UserStatus status,
                                    String name,
                                    String gender,
                                    String birthyear,
                                    String email
    ){
        return User.builder()
                .oauthId(oauthId)
                .oauthType(oauthType)
                .authority(authority)
                .nickName(nickName)
                .status(status)
                .name(name)
                .gender(gender)
                .birthyear(birthyear)
                .email(email)
                .build();
    }

    public User changeNickName (String nickName){
        this.nickName = nickName;
        return this;
    }

    public User changeProFileImg (Long fileGroupId){
        this.fileGroupId = fileGroupId;
        return this;
    }

    public User changeUserStatus (UserStatus status){
        this.status = status;
        return this;
    }

    public UserInfoResponseDto toResponseDto() {
        return new UserInfoResponseDto(
                this.id,
                this.nickName,
                "",
                this.oauthId,
                this.oauthType,
                this.authority,
                this.status
        );
    }


}
