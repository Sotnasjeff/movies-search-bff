package com

import com.configuration.di.configurationModule
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.slf4j.LoggerFactory


@ExperimentalCoroutinesApi
fun main() {
    var managerServer: ApplicationEngine? = null
    val log = LoggerFactory.getLogger("ktor.application")

    runCatching {
        val kodein = Kodein {
            import(configurationModule)
        }

        embeddedServer(Netty, applicationEngineEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load().resolve())
            val serverPort =
                HoconApplicationConfig(ConfigFactory.load().resolve()).property("ktor.deployment.port").getString()
                    .toInt()

            connector {
                host = "0.0.0.0"
                port = serverPort
            }
            module {
                applicationRoutesModule(kodein)
            }
        }).start(wait = false)

        managerServer = embeddedServer(Netty, applicationEngineEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load().resolve())
            val serverPort = config.property("ktor.management.port").getString().toInt()

            connector {
                host = "0.0.0.0"
                port = serverPort
            }

            module {
                monitorRoutesModule(kodein)
            }
        }, configure = {
            callGroupSize = 1
            workerGroupSize = 1
            connectionGroupSize = 1
        }).start(wait = true)
    }.onFailure {
        log.error("Could not start server due to error", it)
        managerServer?.stop(0, 10000)
    }
}