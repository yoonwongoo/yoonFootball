package com.spring.yoon.football.config;

import com.spring.yoon.football.converter.EnumConvertorFactory;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


    @Value("${file.path}")
    private String path;

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        /*커스텀을 해서 그런지 제일 default보다 밑에 우선 순위인듯...*/
       // resolvers.add( 1,new CustomHandlerExceptionResolver());

    }
    /*쿼리 파라미터 소문자 규칙을 지키기 위해 enum을 처리함 대문자로*/
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConvertorFactory());
    }

    @Override
    	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    		WebMvcConfigurer.super.addResourceHandlers(registry);

    		registry
    				.addResourceHandler("/upload/**")
    				.addResourceLocations("file:///" + path)
    				.setCachePeriod(60 * 10 * 6)
    				.resourceChain(true)
    				.addResolver(new PathResourceResolver());
    	}

    /*restemplate와 달리 builder를 빈으로 등록을 autoConfig해줌.*/
    @Bean
    public WebClient webClient(WebClient.Builder builder){

        HttpClient httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
          .responseTimeout(Duration.ofMillis(5000))
          .doOnConnected(conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS)));


        return builder.baseUrl("http://localhost:9001")
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }


}
