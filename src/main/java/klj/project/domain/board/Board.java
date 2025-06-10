package klj.project.domain.board;

import jakarta.persistence.*;
import klj.project.web.dto.admin.board.BoardMngrResDto;
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
    private String brandCodeId;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "del_yn")
    private String delYn;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Column(name = "board_category_code_id")
    private String boardCategoryCodeId;

    @Builder
    public Board(String boardName, String brandCodeId, String useYn, String boardCategoryCodeId) {
        this.boardName = boardName;
        this.brandCodeId = brandCodeId;
        this.useYn = useYn;
        this.boardCategoryCodeId = boardCategoryCodeId;
    }

    public static Board createBoard (String boardName, String brandCodeId, String useYn, String boardCategoryCodeId){
        return Board.builder()
                .boardName(boardName)
                .brandCodeId(brandCodeId)
                .useYn(useYn)
                .boardCategoryCodeId(boardCategoryCodeId)
                .build();
    }

    public BoardMngrResDto toResponseDto() {
        return new BoardMngrResDto(
                this.boardId,
                this.boardName,
                this.brandCodeId,
                this.useYn,
                this.delYn,
                this.createDate, // responseDto의 createdDate와 매핑됩니다.
                this.modifyDate,
                this.boardCategoryCodeId
        );
    }



}
