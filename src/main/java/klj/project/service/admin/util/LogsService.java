package klj.project.service.admin.util;

import klj.project.domain.board.Board;
import klj.project.domain.board.Nuinfo;
import klj.project.domain.file.FileCategory;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.repository.board.BoardRepository;
import klj.project.repository.board.NuinfoRepository;
import klj.project.repository.file.FileGroupRepository;
import klj.project.repository.file.FileQuerydslRepository;
import klj.project.repository.file.FileRepository;
import klj.project.repository.util.LogsQuerydslRepository;
import klj.project.repository.util.LogsRepository;
import klj.project.util.FileManageUtil;
import klj.project.web.dto.admin.board.BoardMngrReqDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.board.NuinfoReqDto;
import klj.project.web.dto.admin.board.NuinfoResDto;
import klj.project.web.dto.admin.util.LogsResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogsService {
    private final LogsRepository logsRepository;
    private final LogsQuerydslRepository logsQuerydslRepository;

    public List<LogsResDto> findLogsList(){
        List<LogsResDto> allLogsList = logsQuerydslRepository.findAllLogsList();
        return allLogsList;
    }

}
