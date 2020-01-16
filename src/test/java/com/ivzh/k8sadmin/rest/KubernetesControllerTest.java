package com.ivzh.k8sadmin.rest;


import com.ivzh.k8sadmin.config.security.JwtProperties;
import com.ivzh.k8sadmin.config.security.JwtTokenProvider;
import com.ivzh.k8sadmin.facade.KubernetesFacade;
import com.ivzh.k8sadmin.repository.UserRepository;
import com.ivzh.k8sadmin.service.CustomUserDetailsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class KubernetesControllerTest {

    @Autowired
    private MockMvc mvc;

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public KubernetesController controller() {
            return new KubernetesController();
        }
        @Bean
        public JwtTokenProvider provider() {
            return new JwtTokenProvider();
        }
        @Bean
        public JwtProperties props() {
            return new JwtProperties();
        }
        @Bean
        public UserDetailsService userDetailsService() {
            return new CustomUserDetailsService();
        }
    }

    @MockBean
    private KubernetesFacade facade;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void initTest() {
        Mockito.when(facade.get("default", false)).thenReturn(new HashSet<>());
    }

    @WithMockUser(value = "user")
    @Test
   // @Ignore
    public void testBasicRestCall() throws Exception {
        mvc
                .perform(get("/deployments/default")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
