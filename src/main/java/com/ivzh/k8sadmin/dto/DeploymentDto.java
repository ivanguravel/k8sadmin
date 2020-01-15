package com.ivzh.k8sadmin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivzh.k8sadmin.domain.StoredDeployment;
import com.ivzh.k8sadmin.util.Utils;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeploymentDto {
    private String name;
    private String dockerImage;
    private Integer replica;
    private Integer port;

    public DeploymentDto(String name) {
        this.name = name;
    }

    public DeploymentDto(Deployment deployment) {
        this.name = deployment.getMetadata().getName();

        Container container = Utils.getFirstOrDefault(deployment.getSpec().getTemplate().getSpec().getContainers(), new Container());
        this.dockerImage = container.getImage();

        this.replica = deployment.getSpec().getReplicas();
        ContainerPort containerPortDescriptor = Utils.getFirstOrDefault(container.getPorts(), new ContainerPort());
        this.port = containerPortDescriptor.getContainerPort();
    }

    public static DeploymentDto of(StoredDeployment storedDeployment) {
        DeploymentDto dto = new DeploymentDto();

        dto.setDockerImage(storedDeployment.getDockerImage());
        dto.setReplica(storedDeployment.getReplica());
        dto.setPort(storedDeployment.getPort());
        dto.setName(storedDeployment.getName());

        return dto;
    }
}
