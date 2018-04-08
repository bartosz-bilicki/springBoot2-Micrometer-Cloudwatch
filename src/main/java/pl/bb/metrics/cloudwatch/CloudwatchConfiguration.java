package pl.bb.metrics.cloudwatch;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import com.amazonaws.util.EC2MetadataUtils;
import io.micrometer.cloudwatch.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableConfigurationProperties(CloudWatchConfigProperties.class)
@Configuration
class CloudwatchConfiguration {

    /*
    TODO: region from EC2MetadataUtils.getEC2InstanceRegion()
     */

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
        CloudWatchMeterRegistry meterRegistry = new CloudWatchMeterRegistry(config, Clock.SYSTEM, amazonCloudWatch);
        meterRegistry.config().commonTags(commonTags());
        return meterRegistry;
    }

    private Iterable<Tag> commonTags() {
        /* TODO: customize commonTags with data from opsworks
        https://docs.aws.amazon.com/opsworks/latest/userguide/attributes-json-opsworks-instance.html
        question: how to get:

        check instance metadata for actual running instance on production stack!
        * opsworks stack name
        * opsworks layer name
        * opsworks machine name

        there are https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/ops-metricscollected.html
        Dimensions for AWS OpsWorks Metrics StackId LayerId

         */
        return Stream.of(
                Tag.of("privateIpAddress",EC2MetadataUtils.getPrivateIpAddress()),
                Tag.of("instanceId",EC2MetadataUtils.getInstanceId())
        ).collect(Collectors.toList());


    }


}
