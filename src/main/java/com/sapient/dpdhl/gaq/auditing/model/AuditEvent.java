package com.sapient.dpdhl.gaq.auditing.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Generic event that may contain any kind of key/value pairs
 *
 * @author Christian Robert
 */

public class AuditEvent implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private Instant timestamp = Instant.now();
    private Map<String, String> properties = new TreeMap<>();
    private Map<String, Double> metrics = new TreeMap<>();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void addProperty(String key, String value) {
        if (value == null) {
            this.getProperties().remove(key);
        } else {
            this.getProperties().put(key, value);
        }
    }
    public Map<String, String> getProperties() {
        return this.properties;
    }
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addMetric(String key, Number value) {
        if (value == null) {
            this.getMetrics().remove(key);
        } else {
            this.getMetrics().put(key, value.doubleValue());
        }
    }
    public Map<String, Double> getMetrics() {
        return this.metrics;
    }
    public void setMetrics(Map<String, Double> metrics) {
        this.metrics = metrics;
    }

}
