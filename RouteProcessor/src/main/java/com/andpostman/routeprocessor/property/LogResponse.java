package com.andpostman.routeprocessor.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@AllArgsConstructor
@Builder
public class LogResponse {

    private Status status;
    private String unid;
    private String message;

    public enum Status {
        SUCCESS,
        TYPE_ERROR,
        XSD_ERROR
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("status", status.name())
                .append("UNID",unid)
                .append("message", message)
                .toString();
    }
}
