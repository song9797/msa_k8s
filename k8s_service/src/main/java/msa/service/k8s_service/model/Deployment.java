package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Metadata_yml_deploy;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Spec_deploy;

@Setter
@Getter
@NoArgsConstructor
public class Deployment {
    private String apiversion;
    private String kind;
    public Metadata_yml_deploy metadata;
    public Spec_deploy spec;
}
