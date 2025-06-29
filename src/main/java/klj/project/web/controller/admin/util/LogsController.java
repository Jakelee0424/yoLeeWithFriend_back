package klj.project.web.controller.admin.util;


import jakarta.persistence.EntityNotFoundException;
import klj.project.domain.util.IpBlock;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.repository.util.IpBlockRepository;
import klj.project.repository.util.LogsRepository;
import klj.project.service.admin.util.LogsService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.admin.board.BoardMngrResDto;
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

    // ipBlock 레포
    private final IpBlockRepository ipBlockRepository;

    // log serveice
    private final LogsService logsService;

    @GetMapping("/logs/all")
    public KljResponse<List<LogsResDto>> findLogsList() {

        try {
            List<LogsResDto> logsList = logsService.findLogsList();

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


    @PostMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Logs> saveLogs(@RequestBody LogsSaveDto logsSaveDto) {

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Logs logs = new Logs(0L,
                    LogsType.GET,
                    "queryString",
                    "localAddr",
                    localDateTime,
                    "requestURI"
            );
            Logs saveLogs = logsRepository.save(logs);

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




}
