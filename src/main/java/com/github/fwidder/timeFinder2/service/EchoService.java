package com.github.fwidder.timeFinder2.service;

import com.github.fwidder.timeFinder2.model.EchoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EchoService {
    public EchoMessage getEcho(){
        log.debug("Creating empty Message");
        return EchoMessage.builder().build();
    }

    public EchoMessage getEcho(String message){
        log.debug("Creating Message from String");
        return EchoMessage.builder().message(message).build();
    }
}
