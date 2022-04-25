package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Spec_stateful;

@Setter
@Getter
@NoArgsConstructor
public class StatefulSet {
    private String apiVersion;
    private String kind;
    public Metadata_yml metadata;
    public Spec_stateful spec;
}
