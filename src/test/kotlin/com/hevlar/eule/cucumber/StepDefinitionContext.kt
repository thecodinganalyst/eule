package com.hevlar.eule.cucumber

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.*

@Component
class StepDefinitionContext: ResultActions {

    @Autowired
    protected lateinit var mvc: MockMvc

    protected lateinit var currResultActions: ResultActions

    fun getCurrentResultAction(): ResultActions {
        return this.currResultActions
    }

    fun setCurrentResultAction(resultActions: ResultActions) {
        this.currResultActions = resultActions
    }

    override fun andExpect(matcher: ResultMatcher): ResultActions {
        return currResultActions.andExpect(matcher)
    }

    override fun andDo(handler: ResultHandler): ResultActions {
        return currResultActions.andDo(handler)
    }

    override fun andReturn(): MvcResult {
        return currResultActions.andReturn()
    }

    fun perform(requestBuilder: RequestBuilder): ResultActions{
        setCurrentResultAction(mvc.perform(requestBuilder))
        return this
    }
}