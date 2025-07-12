package klj.project.service.admin.menu;

import klj.project.domain.menu.Menu;
import klj.project.repository.menu.MenuRepository;
import klj.project.web.dto.admin.menu.MenuReqDto;
import klj.project.web.dto.admin.menu.MenuResDto;
import klj.project.web.dto.admin.menu.MenuSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuMngService {
    private final MenuRepository menuRepository;

    // Tree 구조의 메뉴 목록을 조회하는 메서드
    public List<MenuResDto> getTreeMenuList() {
        List<Menu> menus = menuRepository.findAllByOrderByOrdAsc();

        List<MenuResDto> menuResDtos = menus.stream()
                .map(Menu::toMenuResDto)
                .toList();

        return buildHierarchy(menuResDtos);
    }

    @Transactional
    public void saveMenuTreeList(MenuSaveDto menuSaveDto) {
        Map<String, Long> tempIdToRealIdMap = new HashMap<>();

        // 1. 삭제
        if (menuSaveDto.getDeleteIdList() != null) {
            for (Long id : menuSaveDto.getDeleteIdList()) {
                menuRepository.deleteById(id);
            }
        }

        // 2. 삽입 - 계층 순서로 들어온다고 가정하고 처리
        if (menuSaveDto.getInsertList() != null) {
            for (MenuReqDto dto : menuSaveDto.getInsertList()) {
                String tempId = dto.getId(); // 프론트에서 보낸 가짜 ID (String)

                Long upperMenuNo = null;
                if (dto.getUpperMenuNo() != null) {
                    String upperId = String.valueOf(dto.getUpperMenuNo());
                    upperMenuNo = tempIdToRealIdMap.getOrDefault(upperId, dto.getUpperMenuNo());
                }

                Menu saved = menuRepository.save(Menu.builder()
                        .menuNm(dto.getMenuNm())
                        .upperMenuNo(upperMenuNo)
                        .url(dto.getUrl())
                        .ord(dto.getOrd())
                        .useYn(dto.getUseYn())
                        .build()
                );

                tempIdToRealIdMap.put(tempId, saved.getMenuNo());
            }
        }

        // 3. 수정
        if (menuSaveDto.getUpdateList() != null) {
            for (MenuReqDto dto : menuSaveDto.getUpdateList()) {
                Menu menu = menuRepository.findById(dto.getMenuNo())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴: " + dto.getMenuNo()));

                menu.setMenuNm(dto.getMenuNm());
                menu.setUpperMenuNo(dto.getUpperMenuNo());
                menu.setUrl(dto.getUrl());
                menu.setOrd(dto.getOrd());
                menu.setUseYn(dto.getUseYn());
            }
        }
    }

    // 계층구조로 변환하는 편의 메서드
    private List<MenuResDto> buildHierarchy(List<MenuResDto> flatList) {
        Map<Long, MenuResDto> dtoMap = new HashMap<>();
        List<MenuResDto> rootList = new ArrayList<>();

        // 메뉴번호 기준으로 Map 구성
        for (MenuResDto dto : flatList) {
            dtoMap.put(dto.getMenuNo(), dto);
        }

        // 계층 구조 설정
        for (MenuResDto dto : flatList) {
            if (dto.getUpperMenuNo() == null) {
                rootList.add(dto); // 루트 메뉴
            } else {
                MenuResDto parent = dtoMap.get(dto.getUpperMenuNo());
                if (parent != null) {
                    parent.getChildren().add(dto);
                } else {
                    rootList.add(dto); // 부모를 못 찾으면 루트로 처리
                }
            }
        }

        return rootList;
    }
}
