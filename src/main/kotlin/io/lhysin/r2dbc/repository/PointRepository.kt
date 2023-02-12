package io.lhysin.r2dbc.repository

import io.lhysin.r2dbc.entity.Point
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono


interface PointRepository: ReactiveCrudRepository<Point, Long> {
    @Query("SELECT NVL(MAX(id), 0) + 1 AS ID FROM ADM.POINT")
    fun findLastId(): Mono<Long>
}