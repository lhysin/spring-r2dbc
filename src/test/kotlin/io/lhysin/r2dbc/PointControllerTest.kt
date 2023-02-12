package io.lhysin.r2dbc

import io.lhysin.r2dbc.dto.CreatePointForm
import io.lhysin.r2dbc.dto.PointRes
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClient
import java.math.BigDecimal
import java.util.stream.IntStream

private val logger = KotlinLogging.logger {}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PointControllerTest {

    @LocalServerPort
    private var port: Int = -1

    // helper methods to create default instances
    fun createDefaultClient(): WebClient {
        val httpClient = HttpClient
            .create()
            .wiretap(true)
        return WebClient.builder()
            .baseUrl("http://localhost:$port")
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }


    @Test
    fun should_createPoint() {
        val client = createDefaultClient()

        var beforeCount = client.get()
            .uri("/point")
            .retrieve()
            .bodyToFlux(PointRes::class.java)
            .collectList()
            .block()
            ?.count()

        val longs = Flux.fromStream(IntStream.range(0, 1000).boxed())
            .concatMap { num ->
                val createPointForm = CreatePointForm(
                    custNo = "C2984310",
                    amount = BigDecimal(1300)
                )

                client.post().uri("/point")
                    .bodyValue(createPointForm)
                    .retrieve()
                    .bodyToMono(Long::class.java)
            }
        longs.collectList().block()

        var afterCount = client.get()
            .uri("/point")
            .retrieve()
            .bodyToFlux(PointRes::class.java)
            .collectList()
            .block()
            ?.count()

        logger.debug("beforeCount : {}, afterCount : {}",beforeCount, afterCount)

    }
}