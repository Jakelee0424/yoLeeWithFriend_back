package klj.project.web.controller.admin.util;


import jakarta.persistence.EntityNotFoundException;
import klj.project.domain.util.IpBlock;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.domain.util.LogsUser;
import klj.project.repository.util.*;
import klj.project.service.admin.util.LogsService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardMngrResDto;
import klj.project.web.dto.admin.common.PageDto;
import klj.project.web.dto.admin.common.PageReqDto;
import klj.project.web.dto.admin.util.IpBlockResDto;
import klj.project.web.dto.admin.util.IpBlockSaveDto;
import klj.project.web.dto.admin.util.LogsResDto;
import klj.project.web.dto.admin.util.LogsSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogsController {

    // log 레포
    private final LogsRepository logsRepository;

    private final LogsUserRepository logsUserRepository;

    private final LogsQuerydslRepository logsQuerydslRepository;

    private final LogsUserQuerydslRepository logsUserQuerydslRepository;

    // ipBlock 레포
    private final IpBlockRepository ipBlockRepository;

    // log serveice
    private final LogsService logsService;

    @GetMapping("/logs/all")
    public KljResponse<PageDto> findLogsList(@ModelAttribute PageReqDto pageReqDto) {

        try {
            List<LogsResDto> logsList = logsService.findLogsList(pageReqDto);
            Long allLogsCount = logsQuerydslRepository.findAllLogsCount(pageReqDto);
            PageDto<LogsResDto> logsResDtoPageDto = new PageDto<>(logsList, allLogsCount);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(logsResDtoPageDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }


    @PostMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<LogsUser> saveUserLogs(@RequestBody LogsSaveDto logsSaveDto) {

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            LogsUser logsUser = new LogsUser(
                    logsSaveDto.getUserId(),
                    LogsType.GET,
                    logsSaveDto.getDescription(),
                    logsSaveDto.getIpAddress(),
                    localDateTime,
                    logsSaveDto.getUrl(),
                    logsSaveDto.getDevice(),
                    logsSaveDto.getBrowser()
            );
            LogsUser saveLogs = logsUserRepository.save(logsUser);


            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(saveLogs);

        }catch (NullPointerException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }catch (UnsupportedOperationException e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }

    }

    @PostMapping(path = "/logs/IpBlock", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<IpBlockResDto> saveIpBlock(@RequestBody IpBlockSaveDto ipBlockSaveDto) {

        try {

            boolean ipBlockIsExistFlag = ipBlockRepository.existsByIpAddress(ipBlockSaveDto.getIpAddress());
            IpBlockResDto ipBlockResDto = new IpBlockResDto();
            if(ipBlockIsExistFlag){
                ipBlockResDto.setMessage("already");
            }else{
                IpBlock save = ipBlockRepository.save(IpBlock.createIpBlock(ipBlockSaveDto.getIpAddress()));
                ipBlockResDto.setMessage("save");
            }

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(ipBlockResDto);

        }catch (NullPointerException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }catch (UnsupportedOperationException e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }

    }

    @PostMapping(path = "/logs/UnIpBlock", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<IpBlockResDto> deleteIpBlock(@RequestBody IpBlockSaveDto ipBlockSaveDto) {

        try {
            IpBlockResDto ipBlockResDto = new IpBlockResDto();
            IpBlock ipBlock = ipBlockRepository.findByIpAddress(ipBlockSaveDto.getIpAddress())
                    .orElseThrow(() -> new EntityNotFoundException("notFoundBlock: " + ipBlockSaveDto.getIpAddress()));


            if(ipBlock !=null){
                ipBlockResDto.setMessage("unBlock");
                ipBlockRepository.delete(ipBlock);
            }else{
                ipBlockResDto.setMessage("notBlock");
            }

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(ipBlockResDto);

        }catch (NullPointerException e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }catch (UnsupportedOperationException e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(null);
        }catch (EntityNotFoundException e){
            IpBlockResDto ipBlockResDto = new IpBlockResDto();
            ipBlockResDto.setMessage("notFoundBlock");
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(ipBlockResDto);
        }

    }

    @PostMapping(path = "/logs/getIpBlock", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<IpBlockResDto> findIpBlock(@RequestBody IpBlockSaveDto ipBlockSaveDto) {

        try {
            boolean ipBlockIsExistFlag = ipBlockRepository.existsByIpAddress(ipBlockSaveDto.getIpAddress());
            IpBlockResDto ipBlockResDto = new IpBlockResDto();

            if(ipBlockIsExistFlag){
                ipBlockResDto.setMessage("Block");
            }else{
                ipBlockResDto.setMessage("notBlock");
            }
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(ipBlockResDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/logs/user/all")
    public KljResponse<PageDto> findUserLogsList(@ModelAttribute PageReqDto pageReqDto) {

        try {
            List<LogsResDto> logsList = logsService.findUserLogsList(pageReqDto);
            Long allLogsCount = logsUserQuerydslRepository.findAllUserLogsCount(pageReqDto);
            PageDto<LogsResDto> logsResDtoPageDto = new PageDto<>(logsList, allLogsCount);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(logsResDtoPageDto);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }

    @GetMapping("/logs/user")
    public KljResponse<List<BoardMngrResDto>> findUserBoardLogsList(@RequestParam Long userId) {

        try {
            List<BoardMngrResDto> logsList = logsService.findUserBoardLogsList(userId);
            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(logsList);

        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }

    }



}
