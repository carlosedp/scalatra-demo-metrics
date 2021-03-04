# Scalatra with Prometheus Metrics Demo App #

This is a demo for Scalatra project with Prometheus metrics

Prometheus exporter is integrated with Dropwizard metrics so it's transparent to generate metrics in Scalatra.

## Build & Run ##

```sh
$ cd scalatra-demo-app
$ sbt
> jetty:start
# or for auto-reload
> ~;jetty:stop;jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

Metrics can be seen (and fetched by Prometheus) at [http://localhost:8080/metrics](http://localhost:8080/metrics).

Dropwizard metrics, ping and health-check endpoints are also available at [http://localhost:8080/metrics-admin](http://localhost:8080/metrics-admin).