package com.elijahbus.megaverse.phasetwo

import com.elijahbus.megaverse.extractColor
import com.elijahbus.megaverse.extractDirection
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Service
class XLogoService(@Value("\${candidate.id}") private val candidateId: String) {

    private final val xlogoRestClient = XLogoRestClient()

    private final val logger: Logger = LoggerFactory.getLogger(XLogoRestClient::class.java)

    val goalMapURL: String =
        "https://coding-challenge-eosin.vercel.app/api/map/$candidateId/goal"

    val polyanetsURL: String = "/polyanets"
    val soloonsURL: String = "/soloons"
    val comethsURL: String = "/comeths"

    @Async
    fun runMap(): Future<String> {
        val completableFuture = CompletableFuture<String>()

        val goalMap: Map<String, Any> = this.getGoalMap();

        // goal = array [ [ " " ] ]
        val placements = (goalMap["goal"] as JsonArray);

        placements.chunked(20).stream().forEachOrdered { chunk ->
            chunk.forEachIndexed rowLoop@{ row, placement ->
                run {
                    (placement.jsonArray).forEachIndexed columnLoop@{ column, element ->
                        run {
                            logger.info("placing element ' $element ' at row ' $row ' and column ' $column ' ")

                            // We don't want to plot "SPACE" to the map, as this will be automatically passed
                            if (!isValidAstralElement(element.toString())) return@columnLoop

                            if (element.toString().contains("COMETH")) {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    comethsURL,
                                    Cometh(row, column, element.toString().extractDirection(), this.candidateId)
                                )
                            }

                            if (element.toString().contains("SOLOON")) {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    soloonsURL,
                                    Soloon(row, column, element.toString().extractColor(), this.candidateId)
                                )
                            }

                            if (element.toString().contains("POLYANET")) {
                                xlogoRestClient.buildRequest(
                                    HttpMethod.POST,
                                    polyanetsURL,
                                    Polyanet(row, column, this.candidateId)
                                )
                            }
                        }
                    }
                }
            }
        }

        completableFuture.complete("Finished plotting astral object on the map !");

        return completableFuture
    }

    /**
     * Retrieve the structure of the MAP
     */
    private fun getGoalMap(): Map<String, Any> {
        val response = xlogoRestClient.buildRequest(
            HttpMethod.GET,
            goalMapURL,
            null
        )

        val jsonResponse = (response as ResponseEntity<*>).body?.let { Json.parseToJsonElement(it as String) }
        require(jsonResponse is JsonObject) { "Could not parse JSON response: $jsonResponse!" }

        return jsonResponse;
    }

    private fun isValidAstralElement(element: String): Boolean {
        val excludedElement = "SPACE";

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

        return validElements.contains(element) && element != excludedElement;
    }
}
