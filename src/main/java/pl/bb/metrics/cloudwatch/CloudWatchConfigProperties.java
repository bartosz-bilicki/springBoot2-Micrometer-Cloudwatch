package pl.bb.metrics.cloudwatch;

import io.micrometer.cloudwatch.CloudWatchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CloudWatchConfigProperties implements CloudWatchConfig {

    @Autowired
    private CloudWatchProperties properties;

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String namespace() {
        return properties.getNamespace();
    }

    @Override
    public int batchSize() {
        return properties.getBatchSize();
    }
}
