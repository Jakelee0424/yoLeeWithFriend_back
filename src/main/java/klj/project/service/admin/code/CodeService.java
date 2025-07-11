package klj.project.service.admin.code;

import klj.project.repository.code.CodeQuerydslRepository;
import klj.project.repository.code.CodeRepository;
import klj.project.web.dto.admin.code.CodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CodeService {

    private final CodeRepository codeRepository;
    private final CodeQuerydslRepository codeQuerydslRepository;

    public List<CodeDto> getCodeList(String codeId){
        List<CodeDto> codeDtoList = codeQuerydslRepository.findByCodeId(codeId);
        
        return codeDtoList;
    }


    public CodeDto getRegionCode(String area, String region) {
        CodeDto regionCode = codeQuerydslRepository.getRegionCode(area, region);

        return regionCode;
    }

    public CodeDto getCode(String codeId){
        CodeDto codeDto = codeRepository.findById(codeId).orElseThrow(() -> new IllegalArgumentException("해당 코드를 찾을 수 없습니다. ID: " + codeId)).toResponseDto();

        return codeDto;
    }

}
