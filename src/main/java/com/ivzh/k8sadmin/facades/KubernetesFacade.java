package com.ivzh.k8sadmin.facades;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.service.KubernetesOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static com.ivzh.k8sadmin.util.Constants.AdminServiceFunctionality.*;

@Component
public class KubernetesFacade {

    @Autowired
    private Map<String, KubernetesOperations> k8sOperations;

    public Set<DeploymentDto> get(String namespace, boolean doUseStorage) {
        return getKubernetesServiceByCondition(doUseStorage).get(namespace);
    }

    public DeploymentDto get(String namespace, String deployment, boolean doUseStorage) {
        return getKubernetesServiceByCondition(doUseStorage).get(namespace, deployment);
    }

    public DeploymentDto create(String namespace, DeploymentDto dto, boolean doUseStorage) {
        if (doUseStorage) {
            // create service in the db and store it's original config inside DB
            k8sOperations.values().stream().forEach(service -> service.create(namespace, dto));
            return  dto;
        }  else {
            return k8sOperations.get(K8S).create(namespace, dto);
        }
    }

    private KubernetesOperations getKubernetesServiceByCondition(boolean doUseStorage) {
        String serviceName = doUseStorage ? DB : K8S;
        // some sort of modern Strategy pattern approach with helps Spring framework
        return k8sOperations.get(serviceName);
    }
}
