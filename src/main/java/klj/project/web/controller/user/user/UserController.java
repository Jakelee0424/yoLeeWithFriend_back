package klj.project.web.controller.user.user;


import klj.project.domain.admin.admin.Admin;
import klj.project.domain.user.user.User;
import klj.project.service.user.user.UserService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.user.user.UserStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    // 관리자  서비스
    private final UserService userService;

    @PostMapping("/user/all")
    public KljResponse<List<User>> findUserList() {

        try {

            List<User> userList = userService.findUserList();



            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(userList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @PutMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Admin> updateUserStatus(@RequestBody UserStatusDto userStatusDto) {

        try {

            userService.updateUserStatus(userStatusDto);

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

    @DeleteMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Admin> deleteUser(@PathVariable("id") Long userSn) {

        try {

            userService.deleteUser(userSn);

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
