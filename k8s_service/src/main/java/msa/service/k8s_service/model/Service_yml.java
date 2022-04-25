package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Spec;
@Setter
@Getter
@NoArgsConstructor
public class Service_yml {
    private String kind;
    private String apiVersion;
    public Metadata_yml metadata;
    public Spec spec;
}
