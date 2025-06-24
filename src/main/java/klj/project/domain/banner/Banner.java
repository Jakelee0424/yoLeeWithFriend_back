package klj.project.domain.banner;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import klj.project.domain.board.Board;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Banner {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bannerd_id")
    private Long bannerId;

    @Column(name = "banner_name")
    private String bannerName;
    
    @Column(name = "valid_days")
    private int validDays;
    
    @Column(name = "level")
    private int level;
    
    @CreatedDate
    private LocalDateTime dueDate;

 
}
