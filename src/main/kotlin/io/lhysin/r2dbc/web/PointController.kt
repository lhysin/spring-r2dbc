package io.lhysin.r2dbc.web

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import io.lhysin.r2dbc.dto.CreatePointForm
import io.lhysin.r2dbc.dto.PointRes
import io.lhysin.r2dbc.service.PointService
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Validated
@RestController
@RequestMapping("/point")
class PointController (
    private val pointService: PointService,
    private val amazonSQSAsync: AmazonSQSAsync,
    private val queueMessagingTemplate: QueueMessagingTemplate,
) {

    @GetMapping("")
    fun findAll(): Flux<PointRes> {
        return pointService.findAll()
    }
    @PostMapping("")
    fun createPoint(@RequestBody createPointForm: Mono<CreatePointForm>): Mono<Long> {
        return createPointForm.flatMap { form -> pointService.createPoint(form) }
    }

    @PostMapping("/sqs")
    fun createPointBySqs() {
        queueMessagingTemplate.convertAndSend("this-is-test-queue", "first message!!!")
    }
    @GetMapping("/sqs")
    fun getPointBySqs(): Any {
        return amazonSQSAsync.receiveMessage(ReceiveMessageRequest("this-is-test-queue")).messages
    }

}