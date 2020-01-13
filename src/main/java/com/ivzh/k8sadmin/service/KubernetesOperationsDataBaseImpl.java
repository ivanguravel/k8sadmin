package com.ivzh.k8sadmin.service;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service(Constants.AdminServiceFunctionality.DB)
public class KubernetesOperationsDataBaseImpl implements KubernetesOperations {

    @Override
    public Set<DeploymentDto> get(String namespace) {
        return null;
    }

    @Override
    public DeploymentDto get(String namespace, String deployment) {
        return null;
    }

    @Override
    public DeploymentDto create(String namespace, DeploymentDto dto) {
        return null;
    }
}
