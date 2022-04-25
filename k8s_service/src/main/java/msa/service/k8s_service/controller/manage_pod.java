package msa.service.k8s_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import msa.service.k8s_service.service.CreateFile;
import msa.service.k8s_service.service.k8s_cmd;

@Controller
@RequestMapping("/get_pod")
public class manage_pod {
    @Autowired
    private CreateFile createFile;

    @Autowired
    private k8s_cmd k8s;

    public void create_deployment_json(String fileName){

    }

    public void create_pod_json(String podName){

    }
}
