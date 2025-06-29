package klj.project.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import klj.project.domain.user.user.User;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.repository.user.user.UserRepository;
import klj.project.repository.util.LogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {



    private final LogsRepository logsRepository;

    private final UserRepository userRepository;

    public LoggerInterceptor(LogsRepository logsRepository, UserRepository userRepository) {
        this.logsRepository = logsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {

        String requestURI = request.getRequestURI();
        String[] requestUriArr = requestURI.split("/");
        String localAddr = request.getLocalAddr();
        String queryString = request.getQueryString();
        String method = request.getMethod();
        LocalDateTime localDateTime = LocalDateTime.now();
        LogsType logsType = LogsType.GET;

        switch (method) {
            case "GET":
                logsType = LogsType.GET;
                break;
            case "POST":
                logsType = LogsType.POST;
                break;
            case "DELETE":
                logsType = LogsType.DELETE;
                break;
            case "PUT":
                logsType = LogsType.PUT;
                break;
            default:
                logsType = LogsType.GET;
        }

        Logs logs = new Logs(0L, logsType, queryString, localAddr, localDateTime, requestURI);
        logsRepository.save(logs);



        return true;


    }


}
