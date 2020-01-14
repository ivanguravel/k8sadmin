package com.ivzh.k8sadmin.dto;

import com.ivzh.k8sadmin.util.Utils;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import lombok.Data;

@Data
public class DeploymentDto {
    private String name;
    private String dockerImage;
    private int replica;
    private int port;

    public static DeploymentDto of(Deployment deployment) {
        DeploymentDto dto = new DeploymentDto();
        dto.setName(deployment.getMetadata().getName());
        dto.setReplica(deployment.getSpec().getReplicas());

        //Container container = Utils.getFirstOrThrow(deployment.getSpec().getTemplate().getSpec().getContainers());
        //dto.setDockerImage(container.getImage());
        //dto.setPort(Utils.getFirstOrThrow(container.getPorts()).getContainerPort());

        return dto;
    }
}
