package io.lhysin.r2dbc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration

@SpringBootApplication(exclude = [ContextRegionProviderAutoConfiguration::class])
class SpringR2dbcApplication
    fun main(args: Array<String>) {
        runApplication<SpringR2dbcApplication>(*args)
    }


