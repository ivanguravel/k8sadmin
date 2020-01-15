# k8sadmin

## admin rest api for k8s clusters.

###How to build docker image: 
`mvn clean install && mvn dockerfile:build`

###Execution modes:
- Local mode: one way - download your own k8s config from cluster and provide it as a mounted volume
Example:
```
docker run -p 8080:8080 -v $(pwd):/k8s -e "KUBERNETES_AUTH_TRYKUBECONFIG=true" -e "KUBECONFIG=/k8s/config" -e "KUBERNETES_AUTH_TRYSERVICEACCOUNT=false" -t ivzh/k8sadmin:0.0.1-SNAPSHOT
```

- Cluster deployment mode: as a pod inside k8s. Prepare service account before execution. Example in the `examples\sa.yaml`
Example:
```
kubectl create ns sysns && kubectl apply -f examples/sa.yaml && kubectl apply -f examples/k8sadmin
```

k8sadmin is using `fabric8 kubernetes client` as a main library for communicating with k8s clusters. 
Other connection modes can be configured by setting up different environment variables from: https://github.com/fabric8io/kubernetes-client/#configuring-the-client