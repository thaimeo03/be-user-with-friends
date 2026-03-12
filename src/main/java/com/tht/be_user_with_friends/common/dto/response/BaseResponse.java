package com.tht.be_user_with_friends.common.dto.response;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
  private int status;
  private String message;
  private String traceId;
  private Instant responseTime;
  private Object data;

  public static BaseResponse success(String message) {
    return BaseResponse.builder()
        .traceId(generateTraceId())
        .responseTime(Instant.now())
        .status(HttpStatus.OK.value())
        .message(message)
        .build();
  }

  public static BaseResponse success(String message, Object data) {
    return BaseResponse.builder()
        .traceId(generateTraceId())
        .responseTime(Instant.now())
        .status(HttpStatus.OK.value())
        .message(message)
        .data(data)
        .build();
  }

  public static BaseResponse success(int status, String message, Object data) {
    return BaseResponse.builder()
        .traceId(generateTraceId())
        .responseTime(Instant.now())
        .status(status)
        .message(message)
        .data(data)
        .build();
  }

  public static BaseResponse error(int status, String message) {
    return BaseResponse.builder()
        .status(status)
        .message(message)
        .traceId(generateTraceId())
        .responseTime(Instant.now())
        .build();
  }

  private static String generateTraceId() {
    return System.currentTimeMillis() + "-" + UUID.randomUUID();
  }
}
