package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class Container_Stateful {
    private String name;
    private String image;
    private String imagePullPolicy;
    public List<Port_deploy> ports;
    public List<Env> env;
}


