package klj.project.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "brand_code_id")
    private String boardCodeId;

    @Column(name = "use_yn")
    private String useYn;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Column(name = "board_category_code_id")
    private String boardCategoryCodeId;

    @Builder
    public Board(String boardName, String boardCodeId, String useYn, String boardCategoryCodeId) {
        this.boardName = boardName;
        this.boardCodeId = boardCodeId;
        this.useYn = useYn;
        this.boardCategoryCodeId = boardCategoryCodeId;
    }

    public static Board createBoard (String boardName, String boardCodeId, String useYn, String boardCategoryCodeId){
        return Board.builder()
                .boardName(boardName)
                .boardCodeId(boardCodeId)
                .useYn(useYn)
                .boardCategoryCodeId(boardCategoryCodeId)
                .build();
    }





}
