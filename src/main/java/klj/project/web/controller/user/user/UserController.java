package klj.project.web.controller.user.user;


import klj.project.domain.admin.admin.Admin;
import klj.project.domain.user.user.User;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.service.user.user.UserService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.LogsResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
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

    // 유저 관리 서비스
    private final UserService userService;

    private final UserQuerydslRepository userQuerydslRepository;
    @PostMapping("/user/all")
    public KljResponse<PageDto> findUserList(@RequestBody PageReqDto pageReqDto) {

        try {

            List<UserInfoResponseDto> userList = userService.findUserList(pageReqDto);
            Long allUserListCount = userQuerydslRepository.findAllUserListCount(pageReqDto);
            PageDto<UserInfoResponseDto> userResDtoPageDto = new PageDto<>(userList, allUserListCount);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(userResDtoPageDto);

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
