package com.example.demo.model.common;

import com.example.demo.model.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Message implements Serializable {

    public static final String CRITICAL_ERROR_CODE = "MIGR_CORE_000";
    public static final String CRITICAL_ERROR_CODE_DESCRIPTION = "Критическая ошибка";

    /**
     * Code.
     */
    @Column(name = "code")
    private String code;

    /**
     * Code description.
     */
    @Column(name = "code_description")
    private String codeDescription;

    /**
     * Text.
     */
    @Column(name = "text")
    private String text;

    /**
     * Type.
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * In which time event happened.
     */
    @Column(name = "time")
    private Date time;

    /**
     * Convenient method for checking is it error message of not.
     *
     * @return {@code true} if message is error and {@code false} otherwise
     */
    @JsonIgnore
    public Boolean isError() {
        return type == MessageType.ERROR;
    }

    /**
     * Create error message.
     *
     * @param code            error code
     * @param codeDescription code description
     * @param text            error text
     * @return message
     */
    public static Message error(String code, String codeDescription, String text) {
        return new Message()
                .setCode(code)
                .setCodeDescription(codeDescription)
                .setText(text)
                .setTime(new Date())
                .setType(MessageType.ERROR);
    }

    /**
     * Create critical error message.
     *
     * @param msg error message text
     * @return message
     */
    public static Message criticalError(String msg) {
        return error(CRITICAL_ERROR_CODE, CRITICAL_ERROR_CODE_DESCRIPTION, msg);
    }
}

