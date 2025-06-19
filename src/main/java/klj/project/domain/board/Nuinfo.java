package klj.project.domain.board;

import jakarta.persistence.*;
import klj.project.web.dto.admin.board.NuinfoResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_code_mapping")
public class Nuinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long sn;

    @Column(name = "code_id")
    private String codeId;

    @Column(name = "board_id")
    private Long boardId;

    private String value;




    @Builder
    public Nuinfo(String codeId, Long boardId, String value) {
        this.codeId = codeId;
        this.boardId = boardId;
        this.value = value;
    }

    public static Nuinfo createNuinfo (String codeId, Long boardId, String value){
        return Nuinfo.builder()
                .codeId(codeId)
                .boardId(boardId)
                .value(value)
                .build();
    }


    public NuinfoResDto toResponseDto() {
        return new NuinfoResDto(
                this.sn,
                this.codeId,
                this.boardId,
                this.value
        );
    }


}
