package pl.bb.metrics.cloudwatch;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import io.micrometer.cloudwatch.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(CloudWatchConfigProperties.class)
@Configuration
class CloudwatchConfiguration {

    @Bean
    AWSCredentialsProvider credentialsProvider() {
      return new DefaultAWSCredentialsProviderChain();
    }

    @Bean
    AmazonCloudWatchAsync amazonCloudWatchAsync(AWSCredentialsProvider credentialsProvider) {
        return AmazonCloudWatchAsyncClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .build();
    }

    /*
     *  TODO: how often publish metrics?
     *
     *  TODO: Metrics NOT to export
     *  tomcat.servlet.*
     *  jvm.buffer.*
     *
     *  TODO: publish service health as metric
     *
     */


    @Bean
    CloudWatchMeterRegistry cloudWatchMeterRegistry(CloudWatchConfigProperties config,AmazonCloudWatchAsync amazonCloudWatch) {
        return new CloudWatchMeterRegistry(config,Clock.SYSTEM,amazonCloudWatch);
    }
}
