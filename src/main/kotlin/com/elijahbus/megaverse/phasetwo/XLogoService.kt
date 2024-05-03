package com.elijahbus.megaverse.phasetwo

import com.elijahbus.megaverse.extractColor
import com.elijahbus.megaverse.extractDirection
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class XLogoService(@Value("\${candidate.id}") private val candidateId: String) {

    private final val xlogoRestClient = XLogoRestClient()

    private final val logger: Logger = LoggerFactory.getLogger(XLogoRestClient::class.java)

    val goalMapURL: String =
        "https://megaverse-store.onrender.com/api/x-logo-data"

    val polyanetsURL: String = "/polyanets"
    val soloonsURL: String = "/soloons"
    val comethsURL: String = "/comeths"

    suspend fun runMap() {

        val goalMap: Map<String, ArrayList<ArrayList<String>>> = this.getGoalMap()

        // goal = array [ [ " " ] ]
        val placements: ArrayList<ArrayList<String>> = goalMap["goal"] as ArrayList<ArrayList<String>>

        placements.forEachIndexed rowLoop@{ row, placement ->
            coroutineScope {
                placement.forEachIndexed columnLoop@{ column, element ->
                    run {
                        logger.info("placing element ' $element ' at row ' $row ' and column ' $column ' ")

                        println("Before validaton check $element")

                        // We don't want to plot "SPACE" to the map, as this will be automatically passed
                        if (!isValidAstralElement(element)) return@columnLoop
                        println("Past validaton check $element")

                        if (element.contains("COMETH")) {
                            logger.info("Sending a new $element")
                            launch {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    comethsURL,
                                    Cometh(row, column, element.extractDirection())
                                )
                            }
                        }

                        if (element.contains("SOLOON")) {
                            logger.info("Sending a new $element")
                            launch {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    soloonsURL,
                                    Soloon(row, column, element.extractColor())
                                )
                            }
                        }

                        if (element.contains("POLYANET")) {
                            logger.info("Sending a new $element")
                            launch {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    polyanetsURL,
                                    Polyanet(row, column)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieve the structure of the MAP
     */
    private suspend fun getGoalMap(): Map<String, ArrayList<ArrayList<String>>> = coroutineScope {
        val response = async {
            xlogoRestClient.buildRequest(
                HttpMethod.GET,
                goalMapURL,
                null
            )
        }

        return@coroutineScope response.await() as Map<String, ArrayList<ArrayList<String>>>
    }

    private fun isValidAstralElement(element: String): Boolean {
        val excludedElement = "SPACE"

        val validElements: List<String> = listOf(
            "POLYANET",
            "RIGHT_COMETH",
            "LEFT_COMETH",
            "UP_COMETH",
            "DOWN_COMETH",
            "WHITE_SOLOON",
            "BLUE_SOLOON",
            "RED_SOLOON",
            "PURPLE_SOLOON",
        )

        return validElements.contains(element) && element != excludedElement
    }
}
