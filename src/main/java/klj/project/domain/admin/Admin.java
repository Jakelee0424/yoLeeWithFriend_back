package klj.project.domain.admin;

import jakarta.persistence.*;
import klj.project.domain.user.user.Authority;
import klj.project.domain.user.user.OauthType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_sn")
    private Long sn;


    @Enumerated(EnumType.STRING)
    private AdminAuthority authority;

    @Column(name = "admin_id")
    private String id;

    private String name;

    private String passWord;




    @Builder
    public Admin(AdminAuthority authority, String id, String name, String passWord) {
        this.authority = authority;
        this.id = id;
        this.name = name;
        this.passWord = passWord;
    }

    public static Admin createAdmin (AdminAuthority authority, String id, String name, String passWord){
        return Admin.builder()
                .authority(authority)
                .id(id)
                .name(name)
                .passWord(passWord)
                .build();
    }

    public Admin changeAdminInfo (String adminName, String adminPassword){
        this.name = adminName;
        this.passWord = adminPassword;
        return this;
    }



}
