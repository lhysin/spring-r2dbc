package io.lhysin.r2dbc.convertor

import io.lhysin.r2dbc.dto.PointRes
import io.lhysin.r2dbc.entity.Point
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class PointResConvertor : Converter<Point, Flux<PointRes>>{

    override fun convert(point: Point): Flux<PointRes> {
        return Flux.just(PointRes(
            id= point.id!!,
            custNo = point.custNo,
            amount = point.amount
        ))
    }
}