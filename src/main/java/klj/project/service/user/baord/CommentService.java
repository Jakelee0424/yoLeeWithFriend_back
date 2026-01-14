package klj.project.service.user.baord;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import klj.project.domain.board.Board;
import klj.project.domain.board.Comment;
import klj.project.domain.user.user.User;
import klj.project.repository.user.board.CommentQuerydslRepository;
import klj.project.repository.user.board.CommentRepository;
import klj.project.web.dto.user.board.BoardRateResDto;
import klj.project.web.dto.user.board.CommentCountResDto;
import klj.project.web.dto.user.board.CommentReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {
    private final CommentQuerydslRepository commentQuerydslRepository;

    @Transactional(readOnly = true)
    public BoardRateResDto getBoardRate(Long boardId) {
        return commentQuerydslRepository.selectBoardRate(boardId);
    }

    public CommentCountResDto getCommentCount(Long userId) {
        CommentCountResDto commentCountResDto = commentQuerydslRepository.getCommentCount(userId);

        return commentCountResDto;
    }
}
