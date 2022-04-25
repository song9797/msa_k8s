package msa.service.k8s_service.service;

import java.io.InputStream;
import java.util.function.Consumer;

import org.springframework.beans.propertyeditors.InputStreamEditor;
import org.springframework.stereotype.Service;

@Service
public class k8s_cmd {
    private String cmd;
    private InputStream inputStream;
    private Consumer<String> consumer;

  
    
    public void apply(){

    }
   
}
