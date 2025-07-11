package klj.project.service.user.user;


import klj.project.domain.user.user.User;
import klj.project.domain.user.user.UserStatus;
import klj.project.repository.user.user.UserQuerydslRepository;
import klj.project.repository.user.user.UserRepository;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.user.user.UserInfoResponseDto;
import klj.project.web.dto.user.user.UserStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserQuerydslRepository userQuerydslRepository;

    public List<UserInfoResponseDto> findUserList(PageReqDto pageReqDto){
        //List<User> userList = userRepository.findAll();
        List<UserInfoResponseDto> allUserList = userQuerydslRepository.findAllUserList(pageReqDto);
        return allUserList;
    }

    public void deleteUser(Long adminSn) throws Exception {
        userRepository.deleteById(adminSn);

    }

    public User updateUserStatus(UserStatusDto userStatusDto) throws Exception {
        User user = userRepository.findById(userStatusDto.getId()).orElseThrow(() -> new Exception("User not found with id: " + userStatusDto.getId()));
        String userStatus = userStatusDto.getStatus();
        UserStatus userStatusObject = UserStatus.valueOf(userStatus);
        User changeUser = user.changeUserStatus(userStatusObject);
        User saveUser = userRepository.save(changeUser);
        return saveUser;
    }



}
