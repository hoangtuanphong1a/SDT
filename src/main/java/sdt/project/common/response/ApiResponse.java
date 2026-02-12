package  sdt.project.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final String status;         // success | error
    private final List<String> messages; // error / warning / info messages
    private final Object metadata;       // pagination, extra info
    private final T data;                // payload

    /* ===========================
       SUCCESS RESPONSES
       =========================== */

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, Object metadata) {
        return ApiResponse.<T>builder()
                .status("success")
                .metadata(metadata)
                .data(data)
                .build();
    }

    /* ===========================
       ERROR RESPONSES
       =========================== */

    public static ApiResponse<Void> error(String message) {
        return ApiResponse.<Void>builder()
                .status("error")
                .messages(List.of(message))
                .build();
    }

    public static ApiResponse<Void> error(List<String> messages) {
        return ApiResponse.<Void>builder()
                .status("error")
                .messages(messages)
                .build();
    }
}