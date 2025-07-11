package klj.project.web.dto.admin.util;

import klj.project.domain.util.LogsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IpBlockSaveDto {

    private Long id;
    private String ipAddress;
}
