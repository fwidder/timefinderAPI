package com.github.fwidder.timeFinder2.controller.rest;

import com.github.fwidder.timeFinder2.model.EchoMessage;
import com.github.fwidder.timeFinder2.model.rest.EchoRequest;
import com.github.fwidder.timeFinder2.service.rest.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/echo")
@Slf4j
public class EchoController {
    private final EchoService echoService;

    public EchoController(EchoService echoService) {
        this.echoService = echoService;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public EchoMessage getEcho(){
        return echoService.getEcho();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public EchoMessage postEcho(@RequestBody EchoRequest request){
        return echoService.getEcho(request.getMessage());
    }
}
