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

    init {
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
            val data = dataTable.asMaps().first()
            context
                .andExpect(
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                ).andExpect(
                    jsonPath("$.id").value(data["id"])
                )
        }

        Then("HttpStatus {int} is expected") { statusCode: Int ->
            context.andExpect(status().`is`(statusCode))
        }

        Then("the following data list is returned") { dataTable: DataTable ->
            val dataList = dataTable.asMaps()
            context.andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$").isArray,
                jsonPath("$.length()").value(dataList.size)
            )
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