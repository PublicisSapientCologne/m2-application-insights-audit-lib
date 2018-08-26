package com.sapient.azure.auditing.applicationinsights;

import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to catch all incoming HTTP calls to be logged by the {@link com.sapient.azure.auditing.AuditService}
 *
 */
class ApplicationInsightsFilter implements Filter {

    private WebRequestTrackingFilter delegee = null;
    private ApplicationInsightsProperties properties;

    ApplicationInsightsFilter(WebRequestTrackingFilter delegee, ApplicationInsightsProperties properties) {
        this.setDelegee(delegee);
        this.setProperties(properties);
    }

    @Override
    public void destroy() {
        this.getDelegee().destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        // We do *not* want to include all requests for example the requests to the actuator endpoint
        // so we do *not* delegate to the actual WebRequestTrackingFilter
        // but simply continue with all other filters
        if (shouldSkip(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            this.getDelegee().doFilter(servletRequest, servletResponse, filterChain);
        }
    }

    private boolean shouldSkip(String requestURI) {
        for (String path : properties.getIgnoredPaths()) {
            if (requestURI.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.getDelegee().init(filterConfig);
    }

    private WebRequestTrackingFilter getDelegee() {
        return this.delegee;
    }
    private void setDelegee(WebRequestTrackingFilter delegee) {
        this.delegee = delegee;
    }

    public void setProperties(ApplicationInsightsProperties properties) {
        this.properties = properties;
    }
}
