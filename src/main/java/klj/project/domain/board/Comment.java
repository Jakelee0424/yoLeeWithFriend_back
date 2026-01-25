package klj.project.domain.board;

import jakarta.persistence.*;
import klj.project.domain.user.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "taste_rate", nullable = false)
    private Integer tasteRate;

    @Column(name = "price_rate", nullable = false)
    private Integer priceRate;

    @Column(name = "ingredient_rate", nullable = false)
    private Integer ingredientRate;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    public Comment(Board board, User user, String content,
                   Integer tasteRate, Integer priceRate, Integer ingredientRate) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.tasteRate = tasteRate;
        this.priceRate = priceRate;
        this.ingredientRate = ingredientRate;
        this.delYn ="N";
        this.regDt = LocalDateTime.now();
    }

    public Comment deleteComment (){
        this.delYn = "Y";
        return this;
    }

    public Comment modifyComment (String content, Integer tasteRate, Integer priceRate, Integer ingredientRate){
        this.content = content;
        this.tasteRate = tasteRate;
        this.priceRate = priceRate;
        this.ingredientRate = ingredientRate;
        return this;
    }
}
