package klj.project.web.dto.admin.code;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class CodeRequestDto {

    private String id;
    private String name;

    public CodeRequestDto(String id, String name){
        this.id = id;
        this.name = name;
    }
}
