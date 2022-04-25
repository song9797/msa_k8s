package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Spec_service_registry{
    public Selector selector;
    public List<Port_registry> ports;
    private String clusterIP;
}
