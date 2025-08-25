package klj.project.web.controller.user.user;


import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.admin.admin.Admin;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.Files;
import klj.project.domain.user.user.User;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.service.user.user.UserService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.LogsResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import klj.project.web.dto.user.user.UserLoginDto;
import klj.project.web.dto.user.user.UserStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    // 유저 관리 서비스
    private final UserService userService;

    private final UserQuerydslRepository userQuerydslRepository;

    private final UserRepository userRepository;

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

    @Operation(summary = "로그인 유저 개인정보 확인", description = "todo: implementation")
    @GetMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> userLoginInfo(Authentication authentication) {
        try {
            if(authentication !=null){
                User user = (User) authentication.getPrincipal();
                log.info(user.getOauthId());
                User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
                String userFilePath = "";
//            if(loginUser.getFileGroup() !=null){
//                FileGroup fileGroup = loginUser.getFileGroup();
//                Long fileGroupId = fileGroup.getId();
//                Files files = fileRepository.findByFileGroupId(fileGroupId).get();
//                userFilePath = files.getFilePath();
//            }
                UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName(),userFilePath);
                return KljResponse.create()
                        .succeed()
                        .buildWith(userDto);
            }else{
                return KljResponse.create()
                        .succeed()
                        .buildWith(null);
            }
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }



}
