# Monitoring Guide - CoopCredit

## Overview

CoopCredit uses a complete observability stack with Prometheus for metrics collection and Grafana for visualization.

---

## Architecture

```
┌─────────────────────┐
│  Microservices      │
│  - credit-app:8080  │
│  - risk-mock:8081   │
│  /actuator/prometheus
└──────────┬──────────┘
           │ scrape (15s)
           ▼
┌─────────────────────┐
│   Prometheus        │
│   Port: 9090        │
│   - Metrics Storage │
│   - Query Engine    │
└──────────┬──────────┘
           │ datasource
           ▼
┌─────────────────────┐
│   Grafana           │
│   Port: 3000        │
│   - Dashboards      │
│   - Visualization   │
└─────────────────────┘
```

---

## Accessing Services

### Prometheus
- **URL**: http://localhost:9090
- **Targets**: http://localhost:9090/targets
- **Graph**: http://localhost:9090/graph

### Grafana
- **URL**: http://localhost:3000
- **Username**: admin
- **Password**: admin
- **Dashboard**: CoopCredit - Microservices Monitoring

---

## Metrics Available

### JVM Metrics
- `jvm_memory_used_bytes` - Memory usage
- `jvm_memory_max_bytes` - Maximum memory
- `jvm_threads_live_threads` - Active threads
- `jvm_threads_daemon_threads` - Daemon threads
- `jvm_gc_pause_seconds` - Garbage collection pauses

### HTTP Metrics
- `http_server_requests_seconds_count` - Request count
- `http_server_requests_seconds_sum` - Total request duration
- `http_server_requests_seconds_bucket` - Request duration histogram

### Database Metrics
- `hikaricp_connections_active` - Active DB connections
- `hikaricp_connections_idle` - Idle DB connections
- `hikaricp_connections_max` - Maximum DB connections
- `hikaricp_connections_pending` - Pending connections

### System Metrics
- `system_cpu_usage` - CPU usage
- `process_uptime_seconds` - Service uptime
- `logback_events_total` - Log events by level

---

## Dashboard Panels

### 1. HTTP Request Rate
Shows requests per second for each endpoint

**Query**:
```promql
rate(http_server_requests_seconds_count{application="credit-application-service"}[1m])
```

### 2. JVM Heap Memory
Gauge showing heap memory usage vs maximum

**Queries**:
```promql
jvm_memory_used_bytes{application="credit-application-service",area="heap"}
jvm_memory_max_bytes{application="credit-application-service",area="heap"}
```

### 3. HTTP Request Duration (p95)
95th percentile of request duration

**Query**:
```promql
histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket{application="credit-application-service"}[1m])) by (le, uri))
```

### 4. JVM Threads
Live and daemon threads over time

**Queries**:
```promql
jvm_threads_live_threads{application="credit-application-service"}
jvm_threads_daemon_threads{application="credit-application-service"}
```

### 5. Database Connection Pool
HikariCP connection pool metrics

**Queries**:
```promql
hikaricp_connections_active{application="credit-application-service"}
hikaricp_connections_idle{application="credit-application-service"}
hikaricp_connections_max{application="credit-application-service"}
```

### 6. Risk Service Request Rate
Requests to risk-central-mock-service

**Query**:
```promql
rate(http_server_requests_seconds_count{application="risk-central-mock-service"}[1m])
```

---

## Useful Prometheus Queries

### Error Rate
```promql
sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m])) by (uri)
```

### Request Success Rate
```promql
sum(rate(http_server_requests_seconds_count{status=~"2.."}[1m])) / sum(rate(http_server_requests_seconds_count[1m]))
```

### Average Response Time
```promql
rate(http_server_requests_seconds_sum[1m]) / rate(http_server_requests_seconds_count[1m])
```

### Top 5 Slowest Endpoints
```promql
topk(5, histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le, uri)))
```

### Database Connection Pool Utilization
```promql
(hikaricp_connections_active / hikaricp_connections_max) * 100
```

---

## Alerting (Future)

Example alert rules to implement:

### High Error Rate
```yaml
- alert: HighErrorRate
  expr: sum(rate(http_server_requests_seconds_count{status=~"5.."}[5m])) > 0.05
  for: 5m
  annotations:
    summary: "High error rate detected"
```

### High Memory Usage
```yaml
- alert: HighMemoryUsage
  expr: (jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) > 0.9
  for: 5m
  annotations:
    summary: "JVM heap memory usage above 90%"
```

### Database Connection Pool Exhaustion
```yaml
- alert: ConnectionPoolExhaustion
  expr: hikaricp_connections_active >= hikaricp_connections_max
  for: 1m
  annotations:
    summary: "Database connection pool exhausted"
```

---

## Troubleshooting

### Prometheus not scraping metrics

1. Check targets status:
```bash
curl http://localhost:9090/api/v1/targets
```

2. Verify services are exposing metrics:
```bash
curl http://localhost:8080/actuator/prometheus
curl http://localhost:8081/actuator/prometheus
```

3. Check Prometheus logs:
```bash
docker logs coopcredit-prometheus
```

### Grafana dashboard not showing data

1. Verify Prometheus datasource:
   - Go to Configuration > Data Sources
   - Test connection to Prometheus

2. Check if metrics exist in Prometheus:
   - Go to http://localhost:9090
   - Run query: `up`

3. Verify time range in Grafana dashboard

### Metrics not updating

1. Check scrape interval in prometheus.yml (default: 15s)
2. Verify services are running:
```bash
docker-compose ps
```

---

## Best Practices

1. **Monitor Key Metrics**
   - Response time (p50, p95, p99)
   - Error rate
   - Request rate
   - Resource utilization

2. **Set Up Alerts**
   - Define SLOs (Service Level Objectives)
   - Create alerts for SLO violations
   - Use appropriate thresholds

3. **Dashboard Organization**
   - Group related metrics
   - Use consistent time ranges
   - Add annotations for deployments

4. **Retention**
   - Prometheus default: 15 days
   - Adjust based on storage capacity
   - Consider long-term storage (Thanos, Cortex)

---

## References

- [Prometheus Documentation](https://prometheus.io/docs/)
- [Grafana Documentation](https://grafana.com/docs/)
- [Spring Boot Actuator Metrics](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics)
- [Micrometer Prometheus](https://micrometer.io/docs/registry/prometheus)
