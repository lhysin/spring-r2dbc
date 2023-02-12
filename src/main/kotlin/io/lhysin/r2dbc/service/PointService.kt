package io.lhysin.r2dbc.service

import io.lhysin.r2dbc.convertor.CreatePointConvertor
import io.lhysin.r2dbc.convertor.PointResConvertor
import io.lhysin.r2dbc.dto.CreatePointForm
import io.lhysin.r2dbc.dto.PointRes
import io.lhysin.r2dbc.repository.PointRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

@Service
class PointService (
    private val createPointConvertor: CreatePointConvertor,
    private val pointResConvertor: PointResConvertor,
    private val pointRepository: PointRepository
) {

    fun findAll(): Flux<PointRes> {
        logger.debug("PointService.findAll()")
        return pointRepository.findAll()
            .flatMap { point -> pointResConvertor.convert(point) }
    }

    fun createPoint(createPointForm: CreatePointForm): Mono<Long> {
        logger.debug("PointService.createPoint()")
        pointRepository.findLastId().map { a -> logger.debug("asd {}", a) }
        return createPointConvertor.convert(createPointForm)
            .flatMap { point -> pointRepository.save(point) }
            .mapNotNull { point -> point.id }
    }
}