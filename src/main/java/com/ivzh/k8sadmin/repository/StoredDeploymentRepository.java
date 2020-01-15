package com.ivzh.k8sadmin.repository;

import com.ivzh.k8sadmin.domain.StoredDeployment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoredDeploymentRepository extends JpaRepository<StoredDeployment, String> {
    List<StoredDeployment> findAllByNamespace(String namespace);
    Optional<StoredDeployment> findByNamespaceAndName(String namespace, String name);
}
