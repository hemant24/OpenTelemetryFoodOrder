> kind create cluster --config kind-config.yaml 

>  kubectl get nodes


> kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

> docker build -t hemant24/rating:latest .

> kind load docker-image hemant24/rating:latest --name food-order

To check if your images is actually loaded into kind cluster do following

- login to container

> docker exec -it food-order-worker2 /bin/bash

> crictl images //this will list out all images



> kubectl -n ingress-nginx get pods

> kubectl -n ingress-nginx exec -it  ingress-nginx-controller-67d59d7bc-x76t8 /bin/bash

> cat /etc/nginx/nginx.conf


> kind delete cluster --name food-order 