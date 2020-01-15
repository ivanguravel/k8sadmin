# k8sadmin

## admin rest api for k8s clusters.
## This app can get/create deployments in the k8s cluster and also get persist deployment samples from H2 db
## H2 used as cache for your deployments history

## Main diagram
- ![diagram](pictures/diagram.png "diagram")
### Users:
`user\password` - can list deployments;
`admin\password` - can list and create deployments

### How to build docker image: 
`mvn clean install`

### Execution modes:
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

### How to run queries:

There are 2 examples: postman collection in `k8sadmin.postman_collection.json` and curl steps

#### 1) Login:
``` 
curl -X POST http://localhost:8080/signin -H "Content-Type:application/json" -d "{\"username\":\"admin\", \"password\":\"password\"}"
```

Response could be: 
```
{"token":"eyJhb..."}
```

#### 2) copy token and get data from `default` namespace:
```
curl -H "Authorization: eyJhb..."  http://localhost:8080/deployments/default/
```

Response:
```
[
    {
        "name": "nginx",
        "dockerImage": "nginx",
        "replica": 1,
        "port": 80
    },
    {
        "name": "httpbin",
        "dockerImage": "docker.io/citizenstig/httpbin",
        "replica": 1,
        "port": 8000
    }
]
```

#### 3) get detailed information about service:
```
curl -H "Authorization: eyJhb..."  http://0.0.0.0:8080/deployments/default/nginx
```

#### 4) create service(requires `admin` access token)
```
curl -H "Authorization: eyJhb..."  http://localhost:8080/deployments/default/nginx
```

#### 5) 