import com.carlosedp.app._
import org.scalatra._
import javax.servlet.ServletContext

// Imports for metrics
import java.util.Arrays
import scala.collection.mutable.Map
import scala.collection.JavaConverters._
import org.scalatra.metrics.MetricsBootstrap
import org.scalatra.metrics.MetricsSupportExtensions._

// Prometheus metrics
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.dropwizard.DropwizardExports
import io.prometheus.client.dropwizard.samplebuilder.{
  CustomMappingSampleBuilder,
  MapperConfig
}
import io.prometheus.client.exporter.MetricsServlet
import io.prometheus.client.hotspot.DefaultExports

// Metrics filter
import io.prometheus.client.filter.MetricsFilter
import java.util.EnumSet
import javax.servlet.DispatcherType

class ScalatraBootstrap extends LifeCycle with MetricsBootstrap {
  override def init(context: ServletContext) {
    val config = new MapperConfig();
    // The match field in MapperConfig is a simplified glob expression that only allows * wildcard.
    // The pattern is formatted as: "com.domain.appname.servletname.metricname.method.status"
    config.setMatch("com.carlosedp.app.*.*.*.*")
    config.setName("com.carlosedp.app")

    // Labels to be extracted from the metric. Key=label name. Value=label template
    var labels: Map[String, String] = Map()
    labels.put("servlet", "${0}")
    labels.put("name", "${1}")
    labels.put("method", "${2}")
    labels.put("status", "${3}")
    config.setLabels(labels.asJava)

    val metricsBuilder = new CustomMappingSampleBuilder(Arrays.asList(config))
    new DropwizardExports(metricRegistry, metricsBuilder).register()
    // Initialize default metrics
    DefaultExports.initialize();

    // Jetty MetricsFilter
    val mf = new MetricsFilter(
      "metricsFilter",
      "Servlet metrics",
      3,
      Array[Double](0.005, 0.01, 0.025, 0.05, 0.075, 0.1, 0.25, 0.5, 0.75, 1,
        2.5, 5, 7.5, 10)
    )
    context.addFilter("metricsFilter", mf)
    context
      .getFilterRegistration("metricsFilter")
      .addMappingForUrlPatterns(
        EnumSet.allOf(classOf[DispatcherType]),
        true,
        "/*"
      )

    // Metrics
    context.mountMetricsAdminServlet("/metrics-admin")
    context.mountHealthCheckServlet("/health")
    context.mountThreadDumpServlet("/thread-dump")
    context.installInstrumentedFilter("/*")
    // Metrics for prometheus
    context.mount(classOf[MetricsServlet], "/metrics")

    // Mount application servlets
    context.mount(new MyScalatraServlet, "/*")
  }
}
