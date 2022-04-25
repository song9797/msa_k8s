package msa.service.k8s_service.service;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
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
import msa.service.k8s_service.dto.ConfigMapKeyRef;
import msa.service.k8s_service.dto.Configmap;
import msa.service.k8s_service.dto.Container;
import msa.service.k8s_service.dto.Container_Stateful;
import msa.service.k8s_service.dto.Data;
import msa.service.k8s_service.dto.Env;
import msa.service.k8s_service.dto.Label;
import msa.service.k8s_service.dto.MatchLabel;
import msa.service.k8s_service.dto.Metadata_template;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Metadata_yml_deploy;
import msa.service.k8s_service.dto.Port;
import msa.service.k8s_service.dto.Port_deploy;
import msa.service.k8s_service.dto.Port_gateway;
import msa.service.k8s_service.dto.Port_registry;
import msa.service.k8s_service.dto.Selector;
import msa.service.k8s_service.dto.Selector_deploy;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Spec_deploy;
import msa.service.k8s_service.dto.Spec_gateway;
import msa.service.k8s_service.dto.Spec_service_registry;
import msa.service.k8s_service.dto.Spec_stateful;
import msa.service.k8s_service.dto.Spec_stateful_service;
import msa.service.k8s_service.dto.Template_spec;
import msa.service.k8s_service.dto.Template_spec_stateful;
import msa.service.k8s_service.dto.Template_stateful;
import msa.service.k8s_service.dto.Template_yml;
import msa.service.k8s_service.dto.ValueFrom;
import msa.service.k8s_service.model.Deployment;
import msa.service.k8s_service.model.Service_gateway;
import msa.service.k8s_service.model.Service_registry;
import msa.service.k8s_service.model.Service_yml;
import msa.service.k8s_service.model.StatefulSet;
import msa.service.k8s_service.model.Service_stateful;
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

    public void deploy_Microservice(String name, String image, int port){

        // List에 들어가는 Object
        Label label = new Label();
        MatchLabel matchLabel = new MatchLabel();
        Container container = new Container();
        Port_deploy containerPort = new Port_deploy();
        


        // FileWriteBuffer에 저장될 obejct들
        Deployment deployment = new Deployment();


        // instance 생성
        deployment.metadata = new Metadata_yml_deploy();
        deployment.metadata.labels = new Label();
        deployment.spec= new Spec_deploy();
        deployment.spec.selector = new Selector_deploy();
        deployment.spec.selector.matchLabels = new MatchLabel();
        deployment.spec.template = new Template_yml();
        deployment.spec.template.metadata = new Metadata_template();
        deployment.spec.template.metadata.labels = new Label();
        deployment.spec.template.spec = new Template_spec();
        deployment.spec.template.spec.containers = new ArrayList<Container>();
        container.ports = new ArrayList<Port_deploy>();

        //------------------Deployment Config
        deployment.setApiversion("apps/v1");
        deployment.setKind("Deployment");
        deployment.metadata.setName(name+"-app");
        deployment.spec.setReplicas(1);
        deployment.spec.selector.matchLabels.setApp(name+"-app");
        
        label.setApp(name+"-app");
        deployment.spec.template.metadata.labels.setApp(name+"-app");

        container.setName(name+"-app");
        container.setImage(image);
        container.setImagePullPolicy("Always");
        containerPort.setContainerPort(port);
        container.ports.add(containerPort);
        deployment.spec.template.spec.containers.add(container);
        
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File(name+"_deployment.json"), deployment);
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
    public void service_Microservice(String name, String image, int port){

        // List에 들어가는 Object
        Port servicePort = new Port();
        

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
            om.writeValue(new File(name+"_service.json"), service);
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
    public void deployGateway( String image, int port){
        deploy_Microservice("gateway", image, port);        
    }

    public void servicegateway(String image, int port){
        String name = "gateway";
        // List에 들어가는 Object
        Port_gateway servicePort = new Port_gateway();
        

        // FileWriteBuffer에 저장될 obejct들
        Service_gateway service = new Service_gateway();
    
        // instance 생성
        service.metadata=new Metadata_yml();
        service.spec = new Spec_gateway();
        service.spec.selector = new Selector();
        service.spec.ports = new ArrayList<Port_gateway>();

        //------------------Service Config
        service.setApiVersion("v1");
        service.setKind("Service");
        service.metadata.setName(name+"-svc");
        service.spec.setType("LoadBalancer");
        servicePort.setPort(80);
        servicePort.setTargetPort(port);
        servicePort.setProtocol("TCP");
        service.spec.ports.add(servicePort);
        service.spec.selector.setApp(name+"-app");
        
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File(name+"_service.json"), service);
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


    // eureka.yaml file write
    public void writeConfigMap(String name, String image, int port){
        // json object 생성
        Configmap configmap = new Configmap();

        // instace 생성
        configmap.metadata= new Metadata_yml();
        configmap.data = new Data();

        configmap.setApiVersion("v1");
        configmap.setKind("ConfigMap");
        configmap.metadata.setName("eureka-cm");
        configmap.data.setEureka_service_address("http://eureka:8761/eureka");
        
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File("configmap.json"), configmap);
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
    public void deployEureka(String name, String image, int port){
        // json object 생성
        StatefulSet statefulSet = new StatefulSet();

        // list에 들어갈 instance
        Container_Stateful container = new Container_Stateful();
        Port_deploy port2 = new Port_deploy();
        Env env = new Env();

        // list 생성
        statefulSet.spec.template.spec.containers = new ArrayList<Container_Stateful>();
        container.ports = new ArrayList<Port_deploy>();
        container.env = new ArrayList<Env>();

        // instace 생성
        statefulSet.metadata = new Metadata_yml();
        statefulSet.spec= new Spec_stateful();
        statefulSet.spec.selector=new Selector_deploy();
        statefulSet.spec.selector.matchLabels = new MatchLabel();
        statefulSet.spec.template = new Template_stateful();
        statefulSet.spec.template.metadata = new Metadata_template();
        statefulSet.spec.template.metadata.labels = new Label();
        statefulSet.spec.template.spec = new Template_spec_stateful();
        env.valueFrom = new ValueFrom();
        env.valueFrom.configMapKeyRef = new ConfigMapKeyRef();


        // 필드 값 입력
        statefulSet.setApiVersion("apps/v1");
        statefulSet.setKind("StatefulSet");
        statefulSet.metadata.setName(name);
        statefulSet.spec.setServiceName(name);
        statefulSet.spec.setReplicas(1);
        statefulSet.spec.selector.matchLabels.setApp(name);
        statefulSet.spec.template.metadata.labels.setApp(name);
        container.setName(name);
        container.setImage(image);
        container.setImagePullPolicy("Always");
        port2.setContainerPort(port);
        container.ports.add(port2);
        env.setName("EUREKA_SERVER_ADDRESS");
        env.valueFrom.configMapKeyRef.setKey("eureka_service_address");
        env.valueFrom.configMapKeyRef.setName("eureka-cm");
        container.env.add(env);
        statefulSet.spec.template.spec.containers.add(container);

        
        
        
      
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File("statefulset.json"), statefulSet);
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
    public void Service_Registry(String name, String image, int port){
        // json object 생성
        Service_registry service = new Service_registry();

        // list에 들어갈 instance
        Port_registry port_registry = new Port_registry();
        
        // list 생성
        service.spec.ports = new ArrayList<Port_registry>();
        service.metadata.labels = new Label();
        
        // instace 생성
        service.metadata=new Metadata_yml_deploy();
        service.spec=new Spec_service_registry();
        service.spec.selector=new Selector();

        // 필드값 set
        service.setApiVersion("v1");
        service.setKind("Service");
        service.metadata.setName(name);
        service.metadata.labels.setApp("eureka");
        service.spec.setClusterIP("None");
        service.spec.selector.setApp("eureka");
        port_registry.setName("eureka");
        port_registry.setPort(port);
        service.spec.ports.add(port_registry);

        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File("service_registry.json"), service);
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
    public void service_StatefulSet(String name, String image, int port){
        // json object 생성
        Service_stateful service_staefulset = new Service_stateful();

        // list에 들어갈 instance
        Port port2 = new Port();
        
        // list 생성
        service_staefulset.spec.ports = new ArrayList<Port>();
        service_staefulset.metadata.labels= new Label();
        
        // instace 생성
        service_staefulset.metadata = new Metadata_yml_deploy();
        service_staefulset.spec= new Spec_stateful_service();
        service_staefulset.spec.selector=new Selector();
        // 필드 값 set
        service_staefulset.setApiVersion("v1");
        service_staefulset.setKind("Service");
        service_staefulset.metadata.setName("eureka-lb");
        service_staefulset.metadata.labels.setApp("eureka");
        service_staefulset.spec.selector.setApp("eureka");
        service_staefulset.spec.setType("NodePort");
        port2.setPort(80);
        port2.setTargetPort(8761);
        service_staefulset.spec.ports.add(port2);
        
        ObjectMapper om = new ObjectMapper(new JsonFactory());
        try {
            om.writeValue(new File("serviceSatefulset.json"), service_staefulset);
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
}
