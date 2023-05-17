# OpenTelemetryFoodOrder
FoodOrder application for evaluating possibilities in open telemetery

# Collector also exposed some metrics which can be monitored for its health

services:
  telemetry:
    metrics:
      address: 0.0.0.0:8888


And allowing prometheus to scrape that information 

  scrape_configs:
    - job_name: 'collector_metrics'
      scrape_interval: 5s
      static_configs:
        - targets: [ 'collector-service:8888' ]

Finally importing grafana dashboard (18309) : https://grafana.com/grafana/dashboards/18309-opentelemetry-collector-data-flow/


# I wanted to added panel to existing dashboard which shows memory/cpu used by collector and what is defined as part of k8

I first enabled k8's metric service by executing following commands


### Deploy metric server
```sh
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

kubectl get deployment metrics-server -n kube-system
```

You need to patch metric server, as metioned [here](https://gist.github.com/sanketsudake/a089e691286bf2189bfedf295222bd43)

```sh
kubectl patch -n kube-system deployment metrics-server --type=json \
  -p '[{"op":"add","path":"/spec/template/spec/containers/0/args/-","value":"--kubelet-insecure-tls"}]'
```

But with this you able to run 'kubectl top pod' command


Then i installed, kube-state-metrics (https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-state-metrics)

```sh
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm install kube-state-metrics  prometheus-community/kube-state-metrics
```

With I started getting static metrics like limit and request defined on pods. 


To get container runtime information I followed this (https://www.cloudforecast.io/blog/cadvisor-and-kubernetes-monitoring-guide/)

but still no luck. Basically, wanted to have this 'container_memory_usage_bytes' metric 

Tried adding annotations as mentioned here : https://stackoverflow.com/questions/65621273/how-to-scrape-metrics-server-to-prometheus-outside-kubernetes-cluster




### list of processor, extensions

https://github.com/open-telemetry/opentelemetry-collector-contrib/tree/main/processor/transformprocessor

https://github.com/open-telemetry/opentelemetry-collector/tree/main/processor/memorylimiterprocessor