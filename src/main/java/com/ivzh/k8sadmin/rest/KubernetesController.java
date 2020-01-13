package com.ivzh.k8sadmin.rest;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.facades.KubernetesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController("/deployments/{namespace}")
public class KubernetesController {

    @Autowired
    private KubernetesFacade facade;

    @GetMapping
    public Set<DeploymentDto> get(@PathVariable("namespace") String namespace,
                                  @RequestParam(name = "useStorage", required = false) boolean doUseStorage) {
        return facade.get(namespace, doUseStorage);
    }

    @GetMapping("/{deployment}")
    public DeploymentDto get(@PathVariable("namespace") String namespace,
                             @PathVariable("deployment") String deployment,
                             @RequestParam(name = "useStorage", required = false) boolean doUseStorage) {
        return facade.get(namespace, deployment, doUseStorage);
    }

    @PostMapping
    public DeploymentDto dto(@PathVariable("namespace") String namespace, @RequestBody DeploymentDto dto, @RequestParam(name = "useStorage", required = false) boolean doUseStorage) {
        return facade.create(namespace, dto, doUseStorage);
    }
}
