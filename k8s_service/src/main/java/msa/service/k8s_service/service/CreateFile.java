package msa.service.k8s_service.service;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.service.k8s_service.dto.Container;
import msa.service.k8s_service.dto.Label;
import msa.service.k8s_service.dto.MatchLabel;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Port;
import msa.service.k8s_service.dto.Selector;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Template_spec;
import msa.service.k8s_service.dto.Template_yml;
import msa.service.k8s_service.model.Deployment;
import msa.service.k8s_service.model.Service_yml;
import msa.service.k8s_service.model.StatefulSet;
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFile {
    private Yaml yaml = new Yaml();
    private InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("frontend.yml"); 
    Iterable<Object> data ;
    private File file = new File("sample.yml");
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private StringWriter writer;

    public void writeFile(){   
        fileWriter=createFW(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        checkfile(bufferedWriter);
             
    }

    public void readFile(String fileName){
        String path = "C:\\Users\\82104\\k8slog\\"+fileName;
        System.out.println(path);
        InputStream is =  this.getClass().getClassLoader().getResourceAsStream(path);
        Map<String, Object> obj = yaml.load(is);
        System.out.println(data);
    }

    private FileWriter createFW(File file){
        try {
            fileWriter= new FileWriter(file);
        } catch (Exception e) {
            System.out.println("file이 존재하지 않습니다.");
            e.printStackTrace();
        }
        return fileWriter;
    }

    private void checkfile(BufferedWriter target){
        if(null != target)
            System.out.println("파일이 성공적으로 생성되었습니다.");
    }

    public void loadMultipleYaml(){
        int count =0;
        for (Object object : yaml.loadAll(inputStream)){
            count++;
            System.out.println(count);
            System.out.println(object);
        }
        
    }

    // msaService.yaml file write
    public void deployMicroservice(String name, String image, int port){
        // File 생성을 위한 객체들 생성 
        file = new File(name+".yml");
        fileWriter=createFW(file);

        // List에 들어가는 Object
        Label label = new Label();
        MatchLabel matchLabel = new MatchLabel();
        Container container = new Container();
        Port containerPort = new Port();
        Port servicePort = new Port();
        
        // FileWrite 속도 향상을 위한 버퍼 사용
        bufferedWriter = new BufferedWriter(fileWriter);

        // FileWriteBuffer에 저장될 obejct들
        Service_yml service = new Service_yml();
        Deployment deployment = new Deployment();

        // 작성할 object를 담을 List
        List<Object> yml = new ArrayList<>();

        // List에 service object와 deployment object 삽입
        yml.add(service);
        yml.add(deployment);

        // instance 생성
        deployment.metadata = new Metadata_yml();
        deployment.metadata.labels = new ArrayList<Label>();
        deployment.spec= new Spec();
        deployment.spec.selector = new Selector();
        deployment.spec.selector.matchLabels = new ArrayList<MatchLabel>();
        deployment.spec.template = new Template_yml();
        deployment.spec.template.metadata = new Metadata_yml();
        deployment.spec.template.metadata.labels = new ArrayList<Label>();
        deployment.spec.template.spec = new Template_spec();
        deployment.spec.template.spec.containers = new ArrayList<Container>();
        container.ports = new ArrayList<Port>();

        service.metadata=new Metadata_yml();
        service.spec = new Spec();
        service.spec.selector = new Selector();
        service.spec.ports = new ArrayList<Port>();
        //------------------Deployment Config
        deployment.setApiversion("apps/v1");
        deployment.setKind("Deployment");
        deployment.metadata.setName(name+"-app");
        deployment.spec.setReplicas(1);
        matchLabel.setApp(name+"-app");
        deployment.spec.selector.matchLabels.add(matchLabel);
        
        label.setApp(name+"-app");
        deployment.metadata.labels.add(label);
        deployment.spec.template.metadata.labels.add(label);

        container.setName(name+"-app");
        container.setImage(image);
        container.setImagePullPolicy("Always");
        containerPort.setContainerPort(port);
        container.ports.add(containerPort);
        deployment.spec.template.spec.containers.add(container);
        //------------------Service Config
        service.setKind("Service");
        service.setApiVersion("v1");
        service.metadata.setName(name+"-svc");
        service.spec.selector.setApp(name+"-app");
        servicePort.setPort(80);
        servicePort.setTargetPort(port);
        service.spec.ports.add(servicePort);
        
        Iterator<Object> iter = yml.iterator();
        System.out.println(yml);
        yaml.dumpAll(iter, bufferedWriter);
        

        
    }

    public void deploy_Microservice(String name, String image, int port){
        // File 생성을 위한 객체들 생성 
        file = new File(name+"_deploy.yml");
        fileWriter=createFW(file);

        // List에 들어가는 Object
        Label label = new Label();
        MatchLabel matchLabel = new MatchLabel();
        Container container = new Container();
        Port containerPort = new Port();
        
        // FileWrite 속도 향상을 위한 버퍼 사용
        bufferedWriter = new BufferedWriter(fileWriter);

        // FileWriteBuffer에 저장될 obejct들
        Service_yml service = new Service_yml();
        Deployment deployment = new Deployment();

        // 작성할 object를 담을 List
        List<Object> yml = new ArrayList<>();

        // List에 service object와 deployment object 삽입
        yml.add(service);
        yml.add(deployment);

        // instance 생성
        deployment.metadata = new Metadata_yml();
        deployment.metadata.labels = new ArrayList<Label>();
        deployment.spec= new Spec();
        deployment.spec.selector = new Selector();
        deployment.spec.selector.matchLabels = new ArrayList<MatchLabel>();
        deployment.spec.template = new Template_yml();
        deployment.spec.template.metadata = new Metadata_yml();
        deployment.spec.template.metadata.labels = new ArrayList<Label>();
        deployment.spec.template.spec = new Template_spec();
        deployment.spec.template.spec.containers = new ArrayList<Container>();
        container.ports = new ArrayList<Port>();

        //------------------Deployment Config
        deployment.setApiversion("apps/v1");
        deployment.setKind("Deployment");
        deployment.metadata.setName(name+"-app");
        deployment.spec.setReplicas(1);
        matchLabel.setApp(name+"-app");
        deployment.spec.selector.matchLabels.add(matchLabel);
        
        label.setApp(name+"-app");
        deployment.metadata.labels.add(label);
        deployment.spec.template.metadata.labels.add(label);

        container.setName(name+"-app");
        container.setImage(image);
        container.setImagePullPolicy("Always");
        containerPort.setContainerPort(port);
        container.ports.add(containerPort);
        deployment.spec.template.spec.containers.add(container);
        
        System.out.println(yml);
        yaml.dump(deployment, bufferedWriter);
        
    }
    public void service_Microservice(String name, String image, int port){
        // File 생성을 위한 객체들 생성 
        file = new File(name+"_service.yml");
        fileWriter=createFW(file);

        // List에 들어가는 Object
        Port servicePort = new Port();
        
        // FileWrite 속도 향상을 위한 버퍼 사용
        bufferedWriter = new BufferedWriter(fileWriter);
        writer = new StringWriter();
        // FileWriteBuffer에 저장될 obejct들
        Service_yml service = new Service_yml();
   
        // instance 생성
        service.metadata=new Metadata_yml();
        service.spec = new Spec();
        service.spec.selector = new Selector();
        service.spec.ports = new ArrayList<Port>();

        //------------------Service Config
        service.setKind("Service");
        service.setApiVersion("v1");
        service.metadata.setName(name+"-svc");
        service.spec.selector.setApp(name+"-app");
        servicePort.setPort(80);
        servicePort.setTargetPort(port);
        service.spec.ports.add(servicePort);
        
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File("service.json"), service);
        } catch (StreamWriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    // gateway.yaml file write
    public void deployGateway(String name, String image, int port){
        file = new File("{}.yml",name);
        fileWriter=createFW(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        
        Deployment deployement = new Deployment();
    }


    // eureka.yaml file write
    public void deployEureka(String name, String image, int port){
        file = new File("{}.yml",name);
        fileWriter=createFW(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        StatefulSet statefulSet = new StatefulSet();
    }
}
