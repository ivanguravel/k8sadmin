package com.ivzh.k8sadmin.repository;

import com.ivzh.k8sadmin.domain.StoredDeployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StoredDeploymentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoredDeploymentRepository repository;

    @Test
    public void testNamespacesReadingQuery() {
        StoredDeployment storedDeployment1 = StoredDeployment.builder().namespace("test").dockerImage("nginx").name("test1").build();
        StoredDeployment storedDeployment2 = StoredDeployment.builder().namespace("test").dockerImage("nginx").name("test2").build();

        entityManager.persist(storedDeployment1);
        entityManager.persist(storedDeployment2);
        entityManager.flush();

        List<StoredDeployment> test = repository.findAllByNamespace("test");

        assertTrue(2 == test.size());
    }

    @Test
    public void testNamespacesAndDeploymentNameReadingQuery() {
        StoredDeployment storedDeployment = StoredDeployment.builder().namespace("test").dockerImage("nginx").name("test123").build();

        entityManager.persist(storedDeployment);
        entityManager.flush();

        Optional<StoredDeployment> byNamespaceAndName = repository.findByNamespaceAndName("test", "test123");
        assertTrue(byNamespaceAndName.isPresent());
        assertEquals("test123", byNamespaceAndName.get().getName());
    }
}
