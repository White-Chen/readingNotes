package com.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

/**
 * \* Created with Chen Zhe on 1/21/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration
@ComponentScan("com.pizza.web")
public class WebFlowConfig
        extends AbstractFlowConfiguration{

    @Bean
    public FlowDefinitionRegistry flowDefinitionRegistry(){
        return getFlowDefinitionRegistryBuilder()
                .setBasePath("/WEB-INF/flows")
                .addFlowLocationPattern("*-flow.xml")
                .build();
    }

    @Bean
    public FlowExecutor flowExecutor(){
        return getFlowExecutorBuilder(flowDefinitionRegistry())
                .build();
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter(){
        FlowHandlerAdapter flowHandlerAdapter =
                new FlowHandlerAdapter();
        flowHandlerAdapter.setFlowExecutor(flowExecutor());
        return flowHandlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(){
        FlowHandlerMapping flowHandlerMapping =
                new FlowHandlerMapping();
        flowHandlerMapping.setFlowRegistry(flowDefinitionRegistry());
        return flowHandlerMapping;
    }
}
