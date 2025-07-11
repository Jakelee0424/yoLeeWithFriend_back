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
public class Logs {

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

    @Builder
    public Logs(Long userId, LogsType logsType, String description, String ipAddress, LocalDateTime createdDate, String url) {
        this.userId = userId;
        this.logsType = logsType;
        this.description = description;
        this.ipAddress = ipAddress;
        this.createdDate = createdDate;
        this.url = url;
    }

    public static  Logs createLogs (
            Long userId, LogsType logsType, String description, String ipAddress, LocalDateTime createdDate,
            String url
    ){
        return Logs.builder()
                .userId(userId)
                .logsType(logsType)
                .description(description)
                .ipAddress(ipAddress)
                .createdDate(createdDate)
                .url(url)
                .build();
    }

}
