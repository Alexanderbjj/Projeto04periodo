package br.unisales.projetos.demo.controllers.error;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@ControllerAdvice
@RequestMapping(value = "/error")
public class CustomErrorController extends ResponseEntityExceptionHandler implements ErrorController {

  @ExceptionHandler
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> errorHandler(Exception ex, HttpServletRequest request, HttpServletResponse respose)
      throws JsonProcessingException {

    Map<String, Object> map = new HashMap<String, Object>();

    Throwable tex = ex;
    LinkedList<String> ll = new LinkedList<>();
    while (tex != null) {
      ll.add(tex.getMessage());
      tex = tex.getCause();
    }
    map.put("exception", ll);
    map.put("url", request.getRequestURL());   

    Integer code = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
    HttpStatus status = (code != null) ? HttpStatus.valueOf(code) : HttpStatus.FORBIDDEN;

    ResponseEntity<Map<String, Object>> ret = new ResponseEntity<>(map, status);

    return ret;
  }

}
