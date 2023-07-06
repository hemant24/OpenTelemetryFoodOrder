const process = require('process');
//const { diag, DiagConsoleLogger, DiagLogLevel } =require('@opentelemetry/api');
const opentelemetry = require("@opentelemetry/sdk-node");

const {
  getNodeAutoInstrumentations,
} = require("@opentelemetry/auto-instrumentations-node");

const { HttpInstrumentation } = require('@opentelemetry/instrumentation-http');
const { MongoDBInstrumentation } = require('@opentelemetry/instrumentation-mongodb')
const { MongooseInstrumentation } = require('@opentelemetry/instrumentation-mongoose');


const {
  OTLPTraceExporter,
} = require("@opentelemetry/exporter-trace-otlp-grpc"); // = require("@opentelemetry/exporter-trace-otlp-http"); If, using http


const { MeterProvider, PeriodicExportingMetricReader, View, 
  HistogramAggregation, InstrumentType, Aggregation } = require('@opentelemetry/sdk-metrics');

const { 
  OTLPMetricExporter 
} = require('@opentelemetry/exporter-metrics-otlp-grpc'); // = require("'@opentelemetry/exporter-metrics-otlp-http"); If, using http

const { Resource } = require('@opentelemetry/resources');
const { SemanticResourceAttributes } = require('@opentelemetry/semantic-conventions');

const { HistogramAggregator } = require('@opentelemetry/sdk-metrics/build/src/aggregator');

const collectorUrl = process.env.OTEL_EXPORTER_OTLP_ENDPOINT ?? 'http://localhost:5555';
const serviceName = process.env.SERVICE_NAME ?? 'rating-service';


//diag.setLogger(new DiagConsoleLogger(), DiagLogLevel.DEBUG);

const collectorOptions = {
  // url is optional and can be omitted - default is http://localhost:4317
  url: `${collectorUrl}/v1/metrics`,
};



const metricExporter = new OTLPMetricExporter(collectorOptions);


class MyHistogramAggregation extends HistogramAggregation {
  createAggregator(_instrument) {
      return MyHistogramAggregation.MY_INSTANCE;
  }
}
MyHistogramAggregation.MY_INSTANCE = new HistogramAggregator([0, 5, 10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000], true)

const sdk = new opentelemetry.NodeSDK({
  resource: new Resource({
    [SemanticResourceAttributes.SERVICE_NAME]: serviceName,
  }),
  //traceExporter,
  metricReader : new PeriodicExportingMetricReader({
    exporter: metricExporter,
    exportIntervalMillis: 1000,
  }) ,
  //metricExporter,
  traceExporter: new OTLPTraceExporter({
    // optional - default url is http://localhost:4318/v1/traces
    url: collectorUrl,
    //url: collectorUrl + '/v1/traces' , //If, using HTTP
    // optional - collection of custom headers to be sent with each request, empty by default
    headers: {},
  })
  //,instrumentations : [new HttpInstrumentation(), new MongooseInstrumentation()]
  
  ,
  instrumentations: [getNodeAutoInstrumentations(
    {
    "@opentelemetry/instrumentation-fs" : {
      enabled : false
    }
  })]

  ,
   views : [new View({
    aggregation: new MyHistogramAggregation(),
    instrumentName: 'http.server.duration',
    instrumentType : InstrumentType.HISTOGRAM,
    meterName : '@opentelemetry/instrumentation-http',
    description : 'The duration of the inbound HTTP request'
  })]
});
sdk.start()
.then(() => console.log('Tracing initialized'))
  .catch((error) => console.log('Error initializing tracing', error));;


// gracefully shut down the SDK on process exit
process.on('SIGTERM', () => {
  sdk.shutdown()
    .then(() => console.log('Tracing terminated'))
    .catch((error) => console.log('Error terminating tracing', error))
    .finally(() => process.exit(0));
});
