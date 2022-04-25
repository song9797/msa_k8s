package msa.service.k8s_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Template_yml{
    public Metadata_template metadata;
    public Template_spec spec;
}
