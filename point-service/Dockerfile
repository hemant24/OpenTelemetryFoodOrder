FROM python:3

WORKDIR /usr/src/app

COPY requirements.txt ./

RUN pip install --no-cache-dir -r requirements.txt


RUN opentelemetry-bootstrap -a install

COPY . .

# CMD ["opentelemetry-instrument", "python", "./app.py" ]

CMD ["opentelemetry-instrument", "flask", "run" , "--host", "0.0.0.0", "-p", "9084"]