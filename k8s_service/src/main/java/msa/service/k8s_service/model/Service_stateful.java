package msa.service.k8s_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.service.k8s_service.dto.Metadata_yml;
import msa.service.k8s_service.dto.Metadata_yml_deploy;
import msa.service.k8s_service.dto.Spec;
import msa.service.k8s_service.dto.Spec_stateful;
import msa.service.k8s_service.dto.Spec_stateful_service;
@Setter
@Getter
@NoArgsConstructor
public class Service_stateful {
    private String kind;
    private String apiVersion;
    public Metadata_yml_deploy metadata;
    public Spec_stateful_service spec;
}
