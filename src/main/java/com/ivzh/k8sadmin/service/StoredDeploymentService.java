package com.ivzh.k8sadmin.service;

import com.ivzh.k8sadmin.domain.StoredDeployment;
import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.repository.StoredDeploymentRepository;
import com.ivzh.k8sadmin.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service(Constants.AdminServiceFunctionality.DB)
public class StoredDeploymentService implements KubernetesOperations {

    @Autowired
    private StoredDeploymentRepository repository;

    @Override
    public Set<DeploymentDto> get(String namespace) {
        List<StoredDeployment> allByNamespace = repository.findAllByNamespace(namespace);
        return allByNamespace.stream().map(DeploymentDto::of).collect(Collectors.toSet());
    }

    @Override
    public DeploymentDto get(String namespace, String deployment) {
        Optional<StoredDeployment> byNamespaceAndName = repository.findByNamespaceAndName(namespace, deployment);
        StoredDeployment storedDeployment = byNamespaceAndName
                .orElseThrow(() -> new IllegalArgumentException(String.format("can't find deployment in %s namespace with %s name", namespace, deployment)));
        return DeploymentDto.of(storedDeployment);
    }

    @Override
    public DeploymentDto create(String namespace, DeploymentDto dto) {
        StoredDeployment storedDeployment = new StoredDeployment(dto, namespace);
        repository.save(storedDeployment);
        return dto;
    }
}
