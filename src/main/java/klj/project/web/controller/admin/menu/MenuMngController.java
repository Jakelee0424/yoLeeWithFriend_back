package klj.project.web.controller.admin.menu;

import klj.project.service.admin.menu.MenuMngService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.menu.MenuResDto;
import klj.project.web.dto.admin.menu.MenuSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuMngController {

    private final MenuMngService menuMngService;

    @GetMapping(path = "/admin/menuMng/getTreeMenuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<MenuResDto>> getTreeMenuList() {
        try {
            List<MenuResDto> menuResDtos = menuMngService.getTreeMenuList();
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(menuResDtos);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }
    }

    @PostMapping(path = "/admin/menuMng/saveTreeMenuList")
    public KljResponse<String> saveTreeMenuList(@RequestBody MenuSaveDto menuSaveDto) {
        try {
            menuMngService.saveMenuTreeList(menuSaveDto);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith("Y");
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "저장에 실패하였습니다."))
                    .buildWith(null);

        }
    }
}
