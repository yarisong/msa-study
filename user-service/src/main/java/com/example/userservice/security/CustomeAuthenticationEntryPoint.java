package com.example.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomeAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private static ExceptionResponse exceptionResponse =
//            new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized!!!", null);


    @Override
    public void commence(HttpServletRequest request, 
                         HttpServletResponse response, 
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("UnAuthorizaed!!! message : " + authException.getMessage());
        //response에 넣기
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        try (OutputStream os = response.getOutputStream()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(os, exceptionResponse);
//            os.flush();
//        }
        
    }
}
