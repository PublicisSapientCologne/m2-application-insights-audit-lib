package com.sapient.dpdhl.gaq.auditing.model;

import java.io.Serializable;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Specialized event that represents a HTTP request (or HTTP like request) that has been performed
 * to an external system
 *
 * @author Christian Robert
 */

public class AuditRequest implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private String source = null;
    private Instant timestamp = Instant.now();
    private Duration duration = null;
    private URL url = null;
    private String responseCode = null;

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

    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Duration getDuration() {
        return this.duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public URL getUrl() {
        return this.url;
    }
    public void setUrl(URL url) {
        this.url = url;
    }

    public String getResponseCode() {
        return this.responseCode;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
