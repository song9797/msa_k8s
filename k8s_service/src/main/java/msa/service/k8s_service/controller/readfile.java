package msa.service.k8s_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import msa.service.k8s_service.service.CreateFile;

@Slf4j
@Controller
@RequestMapping("/service")
public class readfile {

    @Autowired
    private CreateFile createFile;

    @GetMapping("/readFile")
    public void readfile(){
        log.info("readfile");
        
        log.info("loadMultipleyaml");
        createFile.readFile("chat.yaml");
        log.info("readFile");
    }
    @PostMapping("/writeFile")
    public void writeFile(){
        createFile.writeFile();
    }
    @GetMapping("/hello")
    public void hello(){
        // createFile.deployMicroservice("frontend", "image", 9000);
        createFile.deploy_Microservice("frontend", "image", 9000);
        createFile.service_Microservice("frontend", "image", 9000);
        createFile.deployGateway("image", 9999);
        createFile.servicegateway("image", 9999);
    }
}
