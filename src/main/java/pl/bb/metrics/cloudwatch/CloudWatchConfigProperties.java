package pl.bb.metrics.cloudwatch;

import io.micrometer.cloudwatch.CloudWatchConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@ConfigurationProperties(value = "management.metrics.export.cloudwatch",ignoreUnknownFields = false)
@Validated
class CloudWatchConfigProperties implements CloudWatchConfig {
    @NotEmpty
    private String namespace;

    @PositiveOrZero
    private int batchSize;

    //metrics are published at step() value, default every 1 minute


    public String getNamespace() {
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



    @Override
    public String namespace() {
        return namespace;
    }

    @Override
    public int batchSize() {
        return batchSize;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
