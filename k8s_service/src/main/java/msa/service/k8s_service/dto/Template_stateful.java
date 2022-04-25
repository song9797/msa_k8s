package msa.service.k8s_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Template_stateful{
    public Metadata_template metadata;
    public Template_spec_stateful spec;
}
