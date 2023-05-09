const process = require('process');

/* This is for enabling debug logs
const { diag, DiagConsoleLogger, DiagLogLevel } =require('@opentelemetry/api');
diag.setLogger(new DiagConsoleLogger(), DiagLogLevel.DEBUG);
*/
const opentelemetry = require("@opentelemetry/sdk-node");
const { ConsoleSpanExporter } = require('@opentelemetry/sdk-trace-base');

const {
  getNodeAutoInstrumentations,
} = require("@opentelemetry/auto-instrumentations-node");

const { HttpInstrumentation } = require('@opentelemetry/instrumentation-http');
const { MongoDBInstrumentation } = require('@opentelemetry/instrumentation-mongodb')
//const { MongooseInstrumentation } = require('@opentelemetry/instrumentation-mongoose'); Mongoose is not supported for node <= 12

/*
const {
  OTLPTraceExporter,
} = require("@opentelemetry/exporter-trace-otlp-grpc"); // Some error with grpc for node <=12

*/

const {
  OTLPTraceExporter,
} = require("@opentelemetry/exporter-trace-otlp-http");


const traceExporter = new ConsoleSpanExporter();


const { MeterProvider, PeriodicExportingMetricReader, View, 
  HistogramAggregation, InstrumentType, Aggregation } = require('@opentelemetry/sdk-metrics');

/*
const { 
  OTLPMetricExporter 
} = require('@opentelemetry/exporter-metrics-otlp-grpc'); // Some error with grpc for node <=12
*/


const { 
  OTLPMetricExporter 
} = require('@opentelemetry/exporter-metrics-otlp-http');


const { Resource } = require('@opentelemetry/resources');
const { SemanticResourceAttributes } = require('@opentelemetry/semantic-conventions');

const { HistogramAggregator } = require('@opentelemetry/sdk-metrics/build/src/aggregator');

const collectorUrl = process.env.OTEL_EXPORTER_OTLP_ENDPOINT ? process.env.OTEL_EXPORTER_OTLP_ENDPOINT : 'http://localhost:5556';
const serviceName = process.env.SERVICE_NAME ? process.env.SERVICE_NAME :  'rating-service';

console.log(collectorUrl)


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
  }),
  //metricExporter,
  //traceExporter : traceExporter
  
  traceExporter: new OTLPTraceExporter({
    // optional - default url is http://localhost:4318/v1/traces
    url: collectorUrl + '/v1/traces',
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
