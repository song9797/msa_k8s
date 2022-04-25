package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class Spec {
    private int replicas;
    private String type;
    public Selector selector;
    public Template_yml template;
    public List<Port> ports;
    private String clusterIP;
    private String serviceName;
}
