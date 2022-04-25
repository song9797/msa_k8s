package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Spec;
@Setter
@Getter
@NoArgsConstructor
public class StatefulSet {
    private String apiVersion;
    private String kind;
    private Metadata_yml metadata_yml;
    private Spec spec;
}
