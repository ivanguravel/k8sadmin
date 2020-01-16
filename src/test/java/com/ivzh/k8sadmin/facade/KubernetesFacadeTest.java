package com.ivzh.k8sadmin.facade;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.facade.KubernetesFacade;
import com.ivzh.k8sadmin.service.KubernetesService;
import com.ivzh.k8sadmin.service.StoredDeploymentService;
import com.ivzh.k8sadmin.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class KubernetesFacadeTest {

    private static final String NAMESPACE = "ns";
    private static final String NAME = "nginx";
    private static final String FROM_K8S = "k8s";
    private static final String FROM_DB = "db";

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public KubernetesFacade facade() { return new KubernetesFacade(); }
    }

    @Autowired
    private KubernetesFacade facade;

    @MockBean(name = Constants.AdminServiceFunctionality.K8S)
    private KubernetesService k8sService;
    @MockBean(name = Constants.AdminServiceFunctionality.DB)
    private StoredDeploymentService storedDeploymentService;

    DeploymentDto k8s = new DeploymentDto(FROM_K8S);

    DeploymentDto db = new DeploymentDto(FROM_DB);

    @Before
    public void setUp() {
        Mockito.when(k8sService.get(NAMESPACE, NAME)).thenReturn(k8s);
        Mockito.when(storedDeploymentService.get(NAMESPACE, NAME)).thenReturn(db);
    }

    @Test
    public void testLogicOfChoosingImplementation() {
        assertEquals(FROM_K8S, facade.get(NAMESPACE, NAME, false).getName());
        assertEquals(FROM_DB, facade.get(NAMESPACE, NAME, true).getName());
    }
}
