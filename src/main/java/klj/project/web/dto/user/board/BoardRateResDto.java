package klj.project.web.dto.user.board;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRateResDto {
    private Double avgTasteRate;
    private Double avgPriceRate;
    private Double avgIngredientRate;
}
