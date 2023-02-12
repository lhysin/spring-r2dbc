package io.lhysin.r2dbc.convertor

import io.lhysin.r2dbc.dto.CreatePointForm
import io.lhysin.r2dbc.entity.Point
import io.lhysin.r2dbc.repository.PointRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreatePointConvertor(
    private val pointRepository: PointRepository

) : Converter<CreatePointForm, Mono<Point>> {

    override fun convert(createPointForm: CreatePointForm): Mono<Point> {

        return pointRepository.findLastId()
            .map { id ->
                Point(
                    id = id,
                    custNo = createPointForm.custNo,
                    amount = createPointForm.amount
                ).insertable()
            }

    }
}