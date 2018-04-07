package pl.bb.metrics.cloudwatch;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ConfigurationProperties(value = "management.metrics.export.cloudwatch",ignoreUnknownFields = false)
class CloudWatchProperties {
    @NotNull
    private String namespace;

    @PositiveOrZero
    private int batchSize;

    String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
