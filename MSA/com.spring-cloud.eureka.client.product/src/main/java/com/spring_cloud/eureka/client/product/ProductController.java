package com.spring_cloud.eureka.client.product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @RefreshScope 애노테이션은 Spring 애플리케이션의 빈이 설정 변경을 반영할 수 있도록 하는 역할을 합니다.
 * 기본적으로 Spring 애플리케이션의 빈은 애플리케이션이 시작될 때 초기화되고, 설정 값이 변경되더라도 해당 빈은 갱신되지 않습니다.
 * 이 애노테이션을 사용하면 /actuator/refresh 엔드포인트를 호출하여 설정 변경 사항을 동적으로 반영할 수 있습니다.
 */
@RefreshScope
@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}") // 애플리케이션이 실행 중인 포트를 주입받습니다.
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("{productId}")
    public String getProduct(@PathVariable String productId) {
        return "Product detail from PORT : " + serverPort + " and message : " + productId ;
    }
}

