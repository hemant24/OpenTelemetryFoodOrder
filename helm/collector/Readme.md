# following this link https://www.aspecto.io/blog/opentelemetry-collector-on-kubernetes-with-helm-chart-part-3/


> helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-charts


> default values are at : https://github.com/open-telemetry/opentelemetry-helm-charts/blob/main/charts/opentelemetry-collector/values.yaml


# To search for specific version of collector

> helm search repo open-telemetry/opentelemetry-collector  --versions


# To get the template ( here the version of chart not app)

> helm template opentelemetry-collector open-telemetry/opentelemetry-collector --version 0.57.1   -f ./collector-values.yml > temp.yml

# To install the collector 

> helm install opentelemetry-collector open-telemetry/opentelemetry-collector --version 0.57.1   -f ./collector-values.yml

# To update helm if you change any value in values.yml

> helm upgrade opentelemetry-collector open-telemetry/opentelemetry-collector --version 0.57.1   -f ./collector-values.yml 




# Following is for grafana oncall but not tested
> helm repo add grafana https://grafana.github.io/helm-charts
> helm repo update
> helm search repo grafana/oncall  --versions

 helm template grafana-oncall grafana/oncall --version 1.2.28   -f ./oncall-values.yml > temp.yml

 helm install grafana-oncall grafana/oncall --version 1.2.28   -f ./oncall-values.yml