package klj.project.domain.board;

import jakarta.persistence.*;
import klj.project.domain.admin.admin.Admin;
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

    @Column(name = "file_group_id")
    private Long fileGroupId;

    @Column(name = "nuinfo_id")
    private String nuinfoId;

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

    @Column(name = "read_cnt")
    private Long readCnt;

    @Builder
    public Board(String boardName, String brandCodeId, String useYn, String boardCategoryCodeId, Long fileGroupId, String nuinfoId, Long readCnt) {
        this.boardName = boardName;
        this.brandCodeId = brandCodeId;
        this.useYn = useYn;
        this.boardCategoryCodeId = boardCategoryCodeId;
        this.fileGroupId = fileGroupId;
        this.nuinfoId = nuinfoId;
        this.delYn = "N";
    }

    public static Board createBoard (String boardName, String brandCodeId, String useYn, String boardCategoryCodeId, Long fileGroupId, String nuinfoId){
        return Board.builder()
                .boardName(boardName)
                .brandCodeId(brandCodeId)
                .useYn(useYn)
                .boardCategoryCodeId(boardCategoryCodeId)
                .fileGroupId(fileGroupId)
                .nuinfoId(nuinfoId)
                .readCnt(0L)
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
                this.boardCategoryCodeId,
                this.nuinfoId,
                "",
                this.readCnt,
                String.valueOf(this.fileGroupId)
        );
    }

    public Board deleteBoard (){
        this.delYn = "Y";
        return this;
    }

    public Board changeBoardInfo (String boardName, String brandCodeId, String useYn, Long fileGroupId){
        this.boardName = boardName;
        this.brandCodeId = brandCodeId;
        this.useYn = useYn;
        this.fileGroupId = fileGroupId;
        return this;
    }


}