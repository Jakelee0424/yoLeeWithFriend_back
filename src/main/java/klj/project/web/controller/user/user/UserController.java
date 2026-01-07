package klj.project.web.controller.user.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.admin.admin.Admin;
import klj.project.domain.file.FileCategory;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.domain.user.user.User;
import klj.project.repository.file.FileGroupRepository;
import klj.project.repository.file.FileQuerydslRepository;
import klj.project.repository.file.FileRepository;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.service.user.user.UserService;
import klj.project.util.FileManageUtil;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardSaveDto;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.LogsResDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import klj.project.web.dto.user.user.UserInfoUpdateDto;
import klj.project.web.dto.user.user.UserLoginDto;
import klj.project.web.dto.user.user.UserStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    // 유저 관리 서비스
    private final UserService userService;

    private final UserQuerydslRepository userQuerydslRepository;

    private final UserRepository userRepository;

    private final FileGroupRepository fileGroupRepository;

    private final FileRepository fileRepository;

    private final FileQuerydslRepository fileQuerydslRepository;

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
                if(loginUser.getFileGroupId() !=null){
                    Long fileGroupId = loginUser.getFileGroupId();
                    Files files = fileRepository.findByFileGroupId(fileGroupId).get();
                    userFilePath = files.getFilePath();
                }
                UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName(),userFilePath, loginUser.getOauthType().toString());
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

    @PutMapping(path = "/user/img", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KljResponse<User> updateUserProFileImg(@RequestParam("data") String data, @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {

        try {
            Long fileGroupId = null;
            ObjectMapper mapper = new ObjectMapper();
            UserLoginDto userLoginDto = mapper.readValue(data, UserLoginDto.class);
            User user = userRepository.findById(userLoginDto.getId()).orElseThrow();
            if(multipartFile !=null){
                if(user.getFileGroupId() != null){
                    FileGroup fileGroup = fileGroupRepository.findById(user.getFileGroupId()).get();
                    fileGroupId = fileGroup.getId();
                    List<Files> files = fileQuerydslRepository.getFileListByFileGroupId(fileGroupId);
                    FileManageUtil.deleteFiles(files);
                    fileRepository.deleteAll(files);
                    LocalDateTime localDateTime = LocalDateTime.now();
                    FileGroup newFileGroup = new FileGroup(FileCategory.img,"게시물이미지",localDateTime);
                    fileGroupRepository.save(newFileGroup);
                    MultipartFile[] multipartFiles = new MultipartFile[1];
                    multipartFiles[0] = multipartFile;
                    List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, newFileGroup, FileType.jpg);
                    fileRepository.saveAll(filesInsertList);
                    fileGroupId = newFileGroup.getId();
                }else{
                    LocalDateTime localDateTime = LocalDateTime.now();
                    FileGroup fileGroup = FileGroup.createFileGroup(FileCategory.img, "게시물이미지", localDateTime);
                    fileGroup = fileGroupRepository.save(fileGroup);
                    fileGroupId = fileGroup.getId();
                    MultipartFile[] multipartFiles = new MultipartFile[1];
                    multipartFiles[0] = multipartFile;
                    List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup, FileType.jpg);
                    fileRepository.saveAll(filesInsertList);
                }
            }
            User changeUser = user.changeProFileImg(fileGroupId);
            userRepository.save(changeUser);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(changeUser);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @PutMapping(path = "/user/{id}/NickName", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<User> updateUserNickName(@PathVariable("id") Long userSn, @RequestBody UserInfoUpdateDto userInfoUpdateDto) {

        try {

            User user = userRepository.findById(userSn).orElseThrow();
            User changeUser = user.changeNickName(userInfoUpdateDto.getNickName());
            userRepository.save(changeUser);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(changeUser);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }



}
