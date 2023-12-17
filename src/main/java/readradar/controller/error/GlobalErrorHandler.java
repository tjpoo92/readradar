package readradar.controller.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler{

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Map<String, String> handeNoSuchElementException(NoSuchElementException e){
        log.info(e.toString());
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("message", e.toString());
        return exceptionMap;
    }
}