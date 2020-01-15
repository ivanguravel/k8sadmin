package com.ivzh.k8sadmin.facade;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import com.ivzh.k8sadmin.facades.KubernetesFacade;
import com.ivzh.k8sadmin.util.Constants;
import org.junit.Before;
import org.junit.Ignore;
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
    private static final String NAME1 = "nginx1";
    private static final String FROM_K8S = "k8s";
    private static final String FROM_DB = "db";

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean(name = Constants.AdminServiceFunctionality.K8S)
        public KubernetesService k8sService() {
            return new KubernetesService();
        }

        @Bean(name = Constants.AdminServiceFunctionality.DB)
        public StoredDeploymentService storedDeploymentService() {
            return new StoredDeploymentService();
        }

        @Bean
        public KubernetesFacade facade() { return new KubernetesFacade(); }
    }

    @Autowired
    private KubernetesFacade facade;

    @MockBean
    private KubernetesService k8sService;
    @MockBean
    private StoredDeploymentService storedDeploymentService;

    DeploymentDto k8s = new DeploymentDto(FROM_K8S);

    DeploymentDto db = new DeploymentDto(FROM_DB);

    @Before
    public void setUp() {
        Mockito.when(k8sService.get(NAMESPACE, NAME)).thenReturn(k8s);
        Mockito.when(k8sService.get(NAMESPACE, NAME1)).thenReturn(db);
    }

    @Test
    public void testLogicOfChoosingImplementation() {
        assertEquals(FROM_K8S, facade.get(NAMESPACE, NAME, false).getName());
    }
}
