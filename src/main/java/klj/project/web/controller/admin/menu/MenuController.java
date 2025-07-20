package klj.project.web.controller.admin.menu;

import klj.project.service.admin.menu.MenuService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.menu.MenuResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    @GetMapping(path = "/getMenuList/{menuNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public KljResponse<List<MenuResDto>> getMenuList(@PathVariable("menuNo") Long menuNo) {
        try {
            List<MenuResDto> adminMenuList = menuService.getMenuList(menuNo);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(adminMenuList);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "메뉴 조회에 실패하였습니다."))
                    .buildWith(null);
        }
    }
}