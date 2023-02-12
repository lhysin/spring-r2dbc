package io.lhysin.r2dbc.web

import io.lhysin.r2dbc.dto.CreatePointForm
import io.lhysin.r2dbc.dto.PointRes
import io.lhysin.r2dbc.service.PointService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/point")
class PointController (
    private val pointService: PointService
) {

    @GetMapping("")
    fun findAll(): Flux<PointRes> {
        return pointService.findAll()
    }
    @PostMapping("")
    fun createPoint(@RequestBody createPointForm: Mono<CreatePointForm>): Mono<Long> {
        return createPointForm.flatMap { form -> pointService.createPoint(form) }
    }
}