package klj.project.web.controller.admin.code;


import klj.project.domain.code.Code;
import klj.project.service.admin.code.CodeService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.code.CodeDto;
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
public class CodeController {

    // 코드 서비스
    private final CodeService codeService;

    @GetMapping(path = "/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<CodeDto> findCodeById(@PathVariable("id") String codeId) {

        try {

            CodeDto code = codeService.getCode(codeId);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(code);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/code/all/{id}")
    public KljResponse<List<CodeDto>> findCodeListByParentId(@PathVariable("id") String parentCodeId) {

        try {

            List<CodeDto> codeList = codeService.getCodeList(parentCodeId);


            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(codeList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }


}
