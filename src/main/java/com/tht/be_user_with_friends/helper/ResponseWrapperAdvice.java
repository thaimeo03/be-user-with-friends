package com.tht.be_user_with_friends.helper;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.tht.be_user_with_friends.common.dto.response.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {
  private final ObjectMapper objectMapper;

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {

    // Prevent double wrapping
    if (body instanceof BaseResponse) {
      return body;
    }

    String path = request.getURI().getPath();
    // Exclude swagger endpoints or other specific paths if needed
    if (path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
      return body;
    }

    int status = HttpStatus.OK.value(); // Default status
    if (response instanceof ServletServerHttpResponse) {
      status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
    }

    BaseResponse baseResponse = BaseResponse.success(status, "Success", body);

    if (body instanceof String) {
      try {
        // If the body is a String, we must return a String because
        // StringHttpMessageConverter will be used.
        // So we manually serialize our BaseResponse to JSON string.
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return objectMapper.writeValueAsString(baseResponse);
      } catch (JsonProcessingException e) {
        log.error("Error converting BaseResponse to JSON string", e);
        return BaseResponse.error(500, "Internal Server Error");
      }
    }

    return baseResponse;
  }

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

}
