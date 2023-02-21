package io.lhysin.r2dbc.config

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.elasticmq.rest.sqs.SQSRestServer
import org.elasticmq.rest.sqs.SQSRestServerBuilder
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile


@Configuration
@Profile(value = ["default", "test", "local"])
class MessageQueueConfig (
    private var elasticMqServerPort: Int = 9324,
) {

    @Bean(destroyMethod="stopAndWait")
    fun elasticMqServer(amazonSQSAsync: AmazonSQSAsync): SQSRestServer {
        val server = SQSRestServerBuilder
            .withPort(elasticMqServerPort)
            .start()
        amazonSQSAsync.createQueue("this-is-test-queue")
        return server
    }

    @Bean
    @Primary
    fun amazonSQSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    "http://localhost:${elasticMqServerPort}", "elasticmq")
            ).build()
    }

    @Bean
    fun queueMessagingTemplate(amazonSQSAsync: AmazonSQSAsync): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQSAsync)
    }


}