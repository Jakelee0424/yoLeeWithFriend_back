package klj.project.web.controller.admin.admin;


import klj.project.domain.admin.admin.Admin;
import klj.project.service.admin.admin.AdminService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.admin.AdminSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    // 관리자  서비스
    private final AdminService adminService;

    @PostMapping("/admin/all")
    public KljResponse<List<Admin>> findAdminList() {

        try {

            List<Admin> adminList = adminService.findAdminList();



            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(adminList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }


    @GetMapping(path = "/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Admin> findAdminBySn(@PathVariable("id") Long adminSn) {

        try {

            Admin admin= adminService.findAdminBySn(adminSn);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(admin);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @PostMapping(path = "/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Admin> saveAdmin(@RequestBody AdminSaveDto adminSaveDto) {

        try {

            Admin admin= adminService.saveAdmin(adminSaveDto);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(admin);

        }catch (NullPointerException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }catch (UnsupportedOperationException e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }

    }

    @DeleteMapping(path = "/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Admin> deleteAdmin(@PathVariable("id") Long adminSn) {

        try {

            adminService.deleteAdmin(adminSn);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

}
