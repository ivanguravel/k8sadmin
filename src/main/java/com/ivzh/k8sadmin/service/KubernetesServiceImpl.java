package com.ivzh.k8sadmin.service;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.util.Constants;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component(Constants.AdminServiceFunctionality.K8S)
public class KubernetesServiceImpl implements KubernetesOperations {

    private final KubernetesClient client;

    public KubernetesServiceImpl() {
                    
        this.client = new DefaultKubernetesClient();
    }

    @Override
    public Set<DeploymentDto> get(String namespace) {
        return client
                .apps()
                .deployments()
                .inNamespace(namespace)
                .list()
                .getItems()
                .stream()
                .map(DeploymentDto::of)
                .collect(Collectors.toSet());
    }

    @Override
    public DeploymentDto get(String namespace, String deployment) {
        Deployment deploymentDescriptor = client.apps().deployments().inNamespace(namespace).withName(deployment).get();
        return DeploymentDto.of(deploymentDescriptor);
    }

    @Override
    public DeploymentDto create(String namespace, DeploymentDto dto) {
        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(dto.getName())
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", dto.getName())
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(dto.getName())
                .withImage(dto.getDockerImage())
                .addNewPort()
                .withContainerPort(dto.getPort())
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                .addToMatchLabels("app", dto.getName())
                .endSelector()
                .endSpec()
                .build();

        return DeploymentDto.of(client.apps().deployments().inNamespace(namespace).createOrReplace(deployment));
    }
}
