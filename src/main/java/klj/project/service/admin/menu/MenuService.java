package klj.project.service.admin.menu;


import klj.project.repository.menu.MenuQuerydslRepository;
import klj.project.web.dto.admin.menu.MenuResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuService {

    private final MenuQuerydslRepository menuQuerydslRepository;

    public List<MenuResDto> getMenuList(Long rootMenuNo) {
        List<MenuResDto> menuResDtos = menuQuerydslRepository.getMenuList();

        // 1. 계층구조를 확인하기 위한 Map 선언 후 값 할당
        Map<Long, MenuResDto> idMap = new HashMap<>();

        for (MenuResDto menuResDto : menuResDtos) {
            idMap.put(menuResDto.getMenuNo(), menuResDto);
        }
        // 2. 하위 메뉴들을 셋팅
        for (MenuResDto menuResDto : menuResDtos) {
            Long parentId = menuResDto.getUpperMenuNo();
            if (parentId != null && idMap.containsKey(parentId)) {
                idMap.get(parentId).getChildren().add(menuResDto);
            }
        }

        // 3. 트리구조를 세팅
        List<MenuResDto> resultList = new ArrayList<>();
        for (MenuResDto menuResDto : menuResDtos) {
            if (menuResDto.getMenuNo().equals(rootMenuNo)) {
                resultList.add(menuResDto);
                break;
            }
        }

        return resultList;
    }
}
