package klj.project.service.admin.login;

import klj.project.domain.admin.Admin;
import klj.project.jwt.CustomAuthorityAdmin;
import klj.project.jwt.TokenProvider;
import klj.project.repository.admin.AdminQuerydslRepository;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.web.dto.user.user.login.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminLoginService {

    private final AdminQuerydslRepository adminQuerydslRepository;

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
