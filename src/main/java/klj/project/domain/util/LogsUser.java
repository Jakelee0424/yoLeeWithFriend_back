package klj.project.domain.util;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class LogsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logs_id")
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private LogsType logsType;

    String description;

    String ipAddress;

    @CreatedDate
    private LocalDateTime createdDate;

    private String url;

    private String device;

    private String browser;

    @Column(name = "board_id")
    private Long boardId;

    @Builder
    public LogsUser(Long userId, LogsType logsType, String description, String ipAddress, LocalDateTime createdDate, String url, String device, String browser, Long boardId) {
        this.userId = userId;
        this.logsType = logsType;
        this.description = description;
        this.ipAddress = ipAddress;
        this.createdDate = createdDate;
        this.url = url;
        this.device = device;
        this.browser = browser;
        this.boardId = boardId;
    }

    public static LogsUser createLogs (
            Long userId, LogsType logsType, String description, String ipAddress, LocalDateTime createdDate,
            String url, String device, String browser
    ){
        return LogsUser.builder()
                .userId(userId)
                .logsType(logsType)
                .description(description)
                .ipAddress(ipAddress)
                .createdDate(createdDate)
                .url(url)
                .device(device)
                .browser(browser)
                .build();
    }

}
