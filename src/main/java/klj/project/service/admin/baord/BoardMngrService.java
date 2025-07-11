package klj.project.service.admin.baord;


import klj.project.domain.admin.Admin;
import klj.project.repository.admin.AdminRepository;
import klj.project.repository.board.BoardQuerydslRepository;
import klj.project.repository.board.BoardRepository;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import klj.project.domain.admin.admin.Admin;
import klj.project.domain.admin.admin.AdminAuthority;
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
import klj.project.util.FileManageUtil;
import klj.project.web.dto.admin.admin.AdminSaveDto;
import klj.project.web.dto.admin.board.BoardMngrReqDto;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.board.NuinfoReqDto;
import klj.project.web.dto.admin.board.NuinfoResDto;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardMngrService {
    private final BoardRepository boardRepository;
    private final BoardQuerydslRepository boardQuerydslRepository;

    public List<BoardMngrResDto> findBoardMngrList(){
        List<BoardMngrResDto> boardMngrList = boardQuerydslRepository.findAllBoardMngrList();
        return boardMngrList;
    }


    private final NuinfoRepository nuinfoRepository;
    private final FileRepository fileRepository;
    private final FileGroupRepository fileGroupRepository;
    private final FileQuerydslRepository fileQuerydslRepository;

    public List<BoardMngrResDto> findBoardMngrList(PageReqDto pageReqDto){
        List<BoardMngrResDto> boardMngrList = boardQuerydslRepository.findAllBoardMngrList(pageReqDto);
        return boardMngrList;
    }

    public BoardMngrResDto findBoardById(Long boardId){
        String imgUrl = "";
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. ID: " + boardId));
        BoardMngrResDto responseDto = board.toResponseDto();
        if(board.getFileGroupId() !=null){
            List<Files> files = fileQuerydslRepository.getFileListByFileGroupId(board.getFileGroupId());
            if(files.size()>0){
                imgUrl = files.get(0).getFilePath();
            }
            responseDto.setImgUrl(imgUrl);
        }

        return responseDto;
    }

    public List<BoardMngrResDto> deleteBoardById(List<Long> boardIds){
        List<BoardMngrResDto> boardDeleteList = new ArrayList<>();
        for(int i=0; i< boardIds.size(); i++){
            Long boardId = boardIds.get(i);
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. ID: " + boardId));
            Board delelteBoard = board.deleteBoard();
            boardRepository.save(delelteBoard);
            boardDeleteList.add(delelteBoard.toResponseDto());
        }

        return boardDeleteList;
    }

    public BoardMngrResDto saveBoard(BoardMngrReqDto boardMngrReqDto, MultipartFile multipartFile) throws Exception {
        Long fileGroupId = null;
        if(boardMngrReqDto.getBoardId() == 0){
            String boardName = boardMngrReqDto.getBoardName();
            String brandCodeId = boardMngrReqDto.getBrandCodeId();


            // 유저 사용여부 개발 이후 삭제
            boardMngrReqDto.setUseYn("Y");
            String useYn = boardMngrReqDto.getUseYn();

            // 메뉴 관리 개발이후 삭제
            boardMngrReqDto.setBoardCategoryCodeId("boardCategory01");
            String boardCategoryCodeId = boardMngrReqDto.getBoardCategoryCodeId();

            // 보충제 영양정보 선택 기능개선 이후 삭제
            boardMngrReqDto.setNuinfoId("nutritionInformation01");
            String nuinfoId = boardMngrReqDto.getNuinfoId();

            if(multipartFile !=null){
                LocalDateTime localDateTime = LocalDateTime.now();
                FileGroup fileGroup = FileGroup.createFileGroup(FileCategory.img, "게시물이미지", localDateTime);
                fileGroup = fileGroupRepository.save(fileGroup);
                fileGroupId = fileGroup.getId();
                MultipartFile[] multipartFiles = new MultipartFile[1];
                multipartFiles[0] = multipartFile;
                List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup, FileType.jpg);
                fileRepository.saveAll(filesInsertList);
            }

            Board board = Board.createBoard(boardName, brandCodeId, useYn, boardCategoryCodeId, fileGroupId, nuinfoId);
            board = boardRepository.save(board);
            return board.toResponseDto();
        }else{
            Board board = boardRepository.findById(boardMngrReqDto.getBoardId()).orElseThrow(() -> new NullPointerException("Board not found with id: " + boardMngrReqDto.getBoardId()));

            if(multipartFile !=null){
                if(board.getFileGroupId() != null){
                    FileGroup fileGroup = fileGroupRepository.findById(board.getFileGroupId()).get();
                    fileGroupId = fileGroup.getId();
                    List<Files> files = fileQuerydslRepository.getFileListByFileGroupId(fileGroupId);
                    FileManageUtil.deleteFiles(files);
                    fileRepository.deleteAll(files);
                    LocalDateTime localDateTime = LocalDateTime.now();
                    FileGroup newFileGroup = new FileGroup(FileCategory.img,"게시물이미지",localDateTime);
                    fileGroupRepository.save(newFileGroup);
                    MultipartFile[] multipartFiles = new MultipartFile[1];
                    multipartFiles[0] = multipartFile;
                    List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, newFileGroup,FileType.jpg);
                    fileRepository.saveAll(filesInsertList);
                    fileGroupId = newFileGroup.getId();
                }else{
                    LocalDateTime localDateTime = LocalDateTime.now();
                    FileGroup fileGroup = FileGroup.createFileGroup(FileCategory.img, "게시물이미지", localDateTime);
                    fileGroup = fileGroupRepository.save(fileGroup);
                    fileGroupId = fileGroup.getId();
                    MultipartFile[] multipartFiles = new MultipartFile[1];
                    multipartFiles[0] = multipartFile;
                    List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup, FileType.jpg);
                    fileRepository.saveAll(filesInsertList);
                }
            }


            String boardName = boardMngrReqDto.getBoardName();
            String brandCodeId = boardMngrReqDto.getBrandCodeId();

            // 유저 사용여부 개발 이후 삭제
            boardMngrReqDto.setUseYn("Y");
            String useYn = boardMngrReqDto.getUseYn();

            Board changeBoard = board.changeBoardInfo(boardName, brandCodeId, useYn, fileGroupId);
            changeBoard = boardRepository.save(changeBoard);
            return changeBoard.toResponseDto();
        }
    }

    public List<NuinfoResDto> saveNuinfo(List<NuinfoReqDto> nuinfoList, Long boardId) throws UnsupportedOperationException, NullPointerException {
        List<NuinfoResDto> nuinfoResList = new ArrayList<>();
        for(int i =0; i<nuinfoList.size(); i++){


            if (nuinfoList.get(i).getSn() != null) {
                Long nuinfoSn = nuinfoList.get(i).getSn();
                Optional<Nuinfo> optionalNuinfo = nuinfoRepository.findById(nuinfoSn);
                Nuinfo nuinfo = optionalNuinfo.get();
                Nuinfo changeNuinfo = nuinfo.changeNuInfo(nuinfoList.get(i).getValue());
                changeNuinfo = nuinfoRepository.save(changeNuinfo);
                nuinfoResList.add(changeNuinfo.toResponseDto());
            }else{
                Nuinfo nuinfo = Nuinfo.createNuinfo(nuinfoList.get(i).getCodeId(), boardId, nuinfoList.get(i).getValue());
                nuinfoRepository.save(nuinfo);
                nuinfoResList.add(nuinfo.toResponseDto());
            }

        }
        return nuinfoResList;
    }
}
