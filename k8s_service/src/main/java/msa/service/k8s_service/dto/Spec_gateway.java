package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Spec_gateway{
    private String type;
    public Selector selector;
    public List<Port_gateway> ports;
}
