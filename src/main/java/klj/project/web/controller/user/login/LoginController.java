package klj.project.web.controller.user.login;


import klj.project.domain.user.user.User;

import klj.project.service.user.login.LoginService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.user.user.login.jwt.TokenDto;
import klj.project.web.dto.user.user.login.oauth.NaverOauthRequestDto;
import klj.project.web.dto.user.user.login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/callback/naver")
    public KljResponse<TokenDto> getNaverAuth(@RequestBody NaverOauthRequestDto naverOauthRequestDto) {

        try {
            String code = naverOauthRequestDto.getCode();
            String state = naverOauthRequestDto.getState();

            // 네이버 AccessToken 받아오기
            OauthTokenDto oauthTokenDto = loginService.getNaverAccessToken(code,state);
            log.info("oauthTokenDto: {}", oauthTokenDto);
            // 네이버 AccessToken으로 네이버 ID값 (순수 pk값 ID 이메일주소 아님) 받아오기
            String oauthId = loginService.getNaverInfo(oauthTokenDto);
            // 네이버 로그인 로직 타기
            User user = loginService.userNaverLogin(oauthId);
            // 내부 JwtToken 발급
            TokenDto tokenDto = loginService.authorize(user);

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

    @PostMapping("/login/callback/kakao")
    public KljResponse<TokenDto> getKakaoAuth(@RequestBody NaverOauthRequestDto naverOauthRequestDto) {

        try {
            String code =naverOauthRequestDto.getCode();
            String state =naverOauthRequestDto.getState();

            // 카카오 AccessToken 받아오기
            OauthTokenDto oauthTokenDto = loginService.getKakaoAccessToken(code,state);
            // 카카오 AccessToken으로 카카오 ID값 (순수 pk값 ID 이메일주소 아님) 받아오기
            String oauthId = loginService.getKakaoInfo(oauthTokenDto);
            // 카카오 로그인 로직 타기
            User user = loginService.userKakaoLogin(oauthId);
            // 내부 JwtToken 발급
            TokenDto tokenDto = loginService.authorize(user);


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
