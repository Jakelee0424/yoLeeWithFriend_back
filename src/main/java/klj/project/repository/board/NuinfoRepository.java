package klj.project.repository.board;

import klj.project.domain.board.Board;
import klj.project.domain.board.Nuinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NuinfoRepository extends JpaRepository<Nuinfo, Long> {

    long count();

    List<Nuinfo> findAllByBoardId(Long boardId);
}
