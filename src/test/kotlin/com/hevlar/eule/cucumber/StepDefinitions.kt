package com.hevlar.eule.cucumber

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class StepDefinitions: En {

    @Autowired
    private lateinit var context: StepDefinitionContext

    private var mapper: ObjectMapper = JsonMapper.builder().findAndAddModules().build()

    private lateinit var data: Map<String, Any?>

    companion object {
        private const val ACCOUNT = "Account"
        private const val JOURNAL_ENTRY = "JournalEntry"
        private val apiRoutes = mapOf(Pair(ACCOUNT, "/accounts"), Pair(JOURNAL_ENTRY, "/journalEntry"))
    }

    init {

        Given("the following {string} exists") { dataTypeName: String, dataTable: DataTable ->
            val jsonList = dataTable.asMaps().map { mapper.writeValueAsString(it) }
            val route = when(dataTypeName){
                "Account" -> "/accounts"
                "JournalEntry" -> "/journalEntry"
                else -> throw IllegalArgumentException("$dataTypeName not recognized")
            }

            jsonList.map {
                context.perform(
                    callApi("POST", route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(it)
                )
            }
        }

        When("{string} is called with {string} with the following data") { route: String, method: String, dataTable: DataTable ->
            val json = dataTable.asMaps().first().let { mapper.writeValueAsString(it) }
            context.perform(
                callApi(method, route)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
        }

        When("{string} is called with {string} with the params {string}") { route: String, method: String, param: String ->
            context.perform(
                callApi(method, "$route$param")
            )
        }

        When("{string} is called with {string}") { route: String, method: String ->
            context.perform(
                callApi(method, route)
            )
        }

        Then("the following data is returned") { dataTable: DataTable ->
            data = dataTable.asMaps().first().conditionMap()
            context
                .andExpect(
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                ).andExpectAll(
                    *data.keys.map {
                        jsonPath("$.$it").value(data[it])
                    }.toTypedArray()
                )
        }

        Then("HttpStatus {int} is expected") { statusCode: Int ->
            context.andExpect(status().`is`(statusCode))
        }

        Then("the following data list is returned") { dataTable: DataTable ->
            val dataList = dataTable.asMaps().map { it.conditionMap() }
            context.andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$").isArray,
                jsonPath("$.length()").value(dataList.size),
                *dataList.flatMapIndexed{ i, map ->
                    map.keys.map {
                        jsonPath("$[$i].$it").value(map[it])
                    }
                }.toTypedArray()
            )
        }

        Then("the data has property {string} with the following data values") { property: String, dataTable: DataTable ->
            val subData = dataTable.asMaps().first().conditionMap()
            context.andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$['$property']").exists(),
                jsonPath("$['$property']").isMap,
                *subData.keys.map {
                    jsonPath("$['$property']['$it']").value(subData[it])
                }.toTypedArray()
            )
        }
    }

    private fun Map<String, String?>.conditionMap(): Map<String, Any?>{
        return this.mapValues {
            it.value?.toDoubleOrNull() ?: it.value
        }
    }

    private fun callApi(method: String, route: String): MockHttpServletRequestBuilder {
        return when(method){
            "GET" -> get(route)
            "POST" -> post(route)
            "PUT" -> put(route)
            "DELETE" -> delete(route)
            else -> throw IllegalArgumentException()
        }
    }

}