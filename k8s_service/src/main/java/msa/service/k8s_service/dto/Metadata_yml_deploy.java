package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Metadata_yml_deploy{
    private String name;
    public Label labels;
}
