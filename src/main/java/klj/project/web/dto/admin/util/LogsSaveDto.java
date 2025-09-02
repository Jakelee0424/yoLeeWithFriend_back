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
public class LogsSaveDto {

    private Long id;
    private Long userId;
    private LogsType logsType;
    private String description;
    private String ipAddress;
    private LocalDateTime createdDate;
    private String url;
    private String device;
    private String browser;
}
