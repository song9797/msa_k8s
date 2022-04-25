package msa.service.k8s_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Configmap {
    private String apiVersion;
    private String kind;
    public Metadata_yml metadata;
    public Data data;
}
