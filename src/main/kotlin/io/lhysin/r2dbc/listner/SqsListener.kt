package io.lhysin.r2dbc.listner

import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class SqsListener {

    @SqsListener(value = ["this-is-test-queue"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun message(message: String){
        logger.debug("receive message!!!! $message")
    }
}