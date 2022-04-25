package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Spec_deploy{
    private int replicas;
    public Selector_deploy selector;
    public Template_yml template;
}
