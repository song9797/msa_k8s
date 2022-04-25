package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Spec_gateway;
@Setter
@Getter
@NoArgsConstructor
public class Service_gateway {
    private String kind;
    private String apiVersion;
    public Metadata_yml metadata;
    public Spec_gateway spec;
}
