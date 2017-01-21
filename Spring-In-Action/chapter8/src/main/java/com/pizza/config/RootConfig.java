package com.pizza.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

/**
 * \* Created with Chen Zhe on 1/21/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration
@ComponentScan(basePackages = {"com.pizza.web"},
        excludeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.CUSTOM,
                    value = {RootConfig.WebPackage.class})
        })
public class RootConfig {
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage(){super(Pattern.compile("com\\.pizza\\.web"));}
    }
}
