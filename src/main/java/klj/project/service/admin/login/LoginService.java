package klj.project.service.admin.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import klj.project.domain.admin.Admin;
import klj.project.domain.user.user.Authority;
import klj.project.domain.user.user.OauthType;
import klj.project.domain.user.user.User;
import klj.project.jwt.CustomAuthority;
import klj.project.jwt.CustomAuthorityAdmin;
import klj.project.jwt.TokenProvider;
import klj.project.repository.admin.AdminQuerydslRepository;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.web.dto.user.user.login.jwt.TokenDto;
import klj.project.web.dto.user.user.login.oauth.IdTokenDto;
import klj.project.web.dto.user.user.login.oauth.NaverInfoResponseDto;
import klj.project.web.dto.user.user.login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {


    private final WebClient webClient;

    private final UserQuerydslRepository userQuerydslRepository;

    private final AdminQuerydslRepository adminQuerydslRepository;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;


    public Admin userAdminLogin(String adminId, String passWord){
        Admin admin = adminQuerydslRepository.findByAdminIdAndPassWord(adminId, passWord);

        log.info("admin info response: {}", admin);
        return admin;
    }

    public TokenDto authorize(Admin admin){

        Collection<CustomAuthorityAdmin> authorities = new ArrayList<>();
        authorities.add(new CustomAuthorityAdmin(admin.getAuthority().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(admin.getSn(), "", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        log.info("jwt: {}", jwt);
        return new TokenDto(jwt);
    }

}
