package klj.project.repository.user.board;

import klj.project.domain.board.Comment;
import klj.project.domain.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_BoardId(Long boardId);
}
