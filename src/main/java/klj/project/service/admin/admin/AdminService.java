package klj.project.service.admin.admin;

import klj.project.domain.admin.Admin;
import klj.project.domain.admin.AdminAuthority;
import klj.project.jwt.CustomAuthorityAdmin;
import klj.project.jwt.TokenProvider;
import klj.project.repository.admin.AdminQuerydslRepository;
import klj.project.repository.admin.AdminRepository;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.web.dto.admin.admin.AdminSaveDto;
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

    public Admin saveAdmin(AdminSaveDto adminSaveDto) throws Exception {
        if(adminSaveDto.getId() == 0){
            String adminId = adminSaveDto.getAdminId();
            String adminName = adminSaveDto.getName();
            String adminPwd = adminSaveDto.getPassWord();
            Admin admin = Admin.createAdmin(AdminAuthority.master, adminId, adminName, adminPwd);
            admin = adminRepository.save(admin);
            return admin;
        }else{
            Admin admin = adminRepository.findById(adminSaveDto.getId()).orElseThrow(() -> new Exception("Admin not found with id: " + adminSaveDto.getId()));
            String adminId = adminSaveDto.getAdminId();
            String adminName = adminSaveDto.getName();
            String adminPwd = adminSaveDto.getPassWord();
            admin = admin.changeAdminInfo(adminName, adminPwd);
            admin = adminRepository.save(admin);
            return admin;
        }
    }



}
