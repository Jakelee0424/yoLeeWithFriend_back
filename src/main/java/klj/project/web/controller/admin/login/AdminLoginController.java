package klj.project.web.controller.admin.login;


import klj.project.domain.admin.Admin;
import klj.project.service.admin.login.AdminLoginService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.login.AdminLoginDto;
import klj.project.web.dto.user.user.login.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminLoginController {

    // 관리자 로그인 서비스
    private final AdminLoginService loginService;

    @PostMapping("/admin/login")
    public KljResponse<TokenDto> loginAdmin(@RequestBody AdminLoginDto adminLoginDto) {

        try {

            String id = adminLoginDto.getId();
            String passWord = adminLoginDto.getPassWord();

            // 관리자 로그인 로직 타기
            Admin admin = loginService.userAdminLogin(id,passWord);

            // 내부 JwtToken 발급
            TokenDto tokenDto = loginService.authorize(admin);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(tokenDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

}
