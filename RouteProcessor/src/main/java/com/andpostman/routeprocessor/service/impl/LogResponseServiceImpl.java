package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.property.LogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.andpostman.routeprocessor.service.LogResponseService;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogResponseServiceImpl implements LogResponseService {

    @Override
    public void printLogResponse(LogResponse response){
        if (response.getStatus() == LogResponse.Status.SUCCESS)
            printSuccessLog(response);
        else if (response.getStatus() == LogResponse.Status.TYPE_ERROR)
            printTypeErrorLog(response);
        else if (response.getStatus() == LogResponse.Status.XSD_ERROR)
            printXSDErrorLog(response);
    }

    private void printSuccessLog(LogResponse response){
        log.info(LogResponse.builder()
                .status(LogResponse.Status.SUCCESS)
                .unid(response.getUnid())
                .message("saved in db: "+response.getMessage())
                .build()
                .toString()
        );
    }

    private void printTypeErrorLog(LogResponse response){
        log.error(LogResponse.builder()
                .status(LogResponse.Status.TYPE_ERROR)
                .message("not valid form type")
                .unid(response.getUnid())
                .build()
                .toString()
        );
    }

    private void printXSDErrorLog(LogResponse response){
        log.error(LogResponse.builder()
                .status(LogResponse.Status.XSD_ERROR)
                .message("not valid field: "+response.getMessage())
                .unid(response.getUnid())
                .build()
                .toString()
        );
    }
}
