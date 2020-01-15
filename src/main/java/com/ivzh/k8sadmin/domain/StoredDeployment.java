package com.ivzh.k8sadmin.domain;

import com.ivzh.k8sadmin.dto.DeploymentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="deployments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredDeployment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;
    private String dockerImage;
    private Integer replica;
    private Integer port;
    private String namespace;

    public StoredDeployment(DeploymentDto dto, String namespace) {
        this.name = dto.getName();
        this.dockerImage = dto.getName();
        this.replica = dto.getReplica();
        this.port = dto.getPort();
        this.namespace = namespace;
    }
}
