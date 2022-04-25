package msa.service.k8s_service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class Selector_deploy{
    public MatchLabel matchLabels;
}
