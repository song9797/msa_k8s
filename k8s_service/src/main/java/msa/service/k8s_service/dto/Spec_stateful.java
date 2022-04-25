package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Spec_stateful{
    private String serviceName;
    private int replicas;
    public Selector_deploy selector;
    public List<Port> ports;
    public Template_stateful template;
}
