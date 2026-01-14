package ru.yandex.practicum.catsgram.exception;

public class ParameterNotValidException extends IllegalArgumentException {

    private String parameter;
    private String reason;

    public ParameterNotValidException(String reason, String parameter) {
        this.reason = reason;
        this.parameter = parameter;
    }

    public String getReason() {
        return reason;
    }

    public String getParameter() {
        return parameter;
    }
}
