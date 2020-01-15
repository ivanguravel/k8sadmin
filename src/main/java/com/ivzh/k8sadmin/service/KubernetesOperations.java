package com.ivzh.k8sadmin.service;

import com.ivzh.k8sadmin.dto.DeploymentDto;

import java.util.Set;

public interface KubernetesOperations {
     Set<DeploymentDto> get(String namespace);
     DeploymentDto get(String namespace, String deployment);
     DeploymentDto create(String namespace, DeploymentDto dto);
}
