package msa.service.k8s_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Port {
    private int containerPort;
    private int targetPort;
    private int port;
    private String protocol;
    private String name;
}
