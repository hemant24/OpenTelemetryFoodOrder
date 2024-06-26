> kind create cluster --config kind-config.yaml 

>  kubectl get nodes

> kind get clusters


> kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

> docker build -t hemant24/rating:latest .
> docker build -t hemant24/order:latest .
> docker build -t hemant24/restaurant:latest .

> kind load docker-image hemant24/rating:latest --name food-order
> kind load docker-image hemant24/order:latest --name food-order
> kind load docker-image hemant24/restaurant:latest --name food-order

To check if your images is actually loaded into kind cluster do following

- login to container

> docker exec -it food-order-worker2 /bin/bash

> crictl images //this will list out all images



> kubectl -n ingress-nginx get pods

> kubectl -n ingress-nginx exec -it  ingress-nginx-controller-67d59d7bc-x76t8 /bin/bash

> cat /etc/nginx/nginx.conf


> kind delete cluster --name food-order 

To apply all yml in a directory

> kubectl apply --recursive -f .

To port forward service 
> kubectl port-forward svc/prometheus-service 9090:9090