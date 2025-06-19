package klj.project.repository.code;

import klj.project.domain.board.Board;
import klj.project.domain.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, String> {

    long count();


}
