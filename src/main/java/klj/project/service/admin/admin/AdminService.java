package klj.project.service.admin.admin;

import klj.project.domain.admin.Admin;
import klj.project.jwt.CustomAuthorityAdmin;
import klj.project.jwt.TokenProvider;
import klj.project.repository.admin.AdminQuerydslRepository;
import klj.project.repository.admin.AdminRepository;
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
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findAdminList(){
        List<Admin> adminList = adminRepository.findAll();
        return adminList;
    }

    public Admin findAdminBySn(Long adminSn) throws Exception {
       /* Admin admin = adminRepository.findById(adminSn)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminSn));*/
        Admin admin = adminRepository.findById(adminSn).orElseThrow(() -> new Exception("Admin not found with id: " + adminSn));
        return admin;
    }



}
