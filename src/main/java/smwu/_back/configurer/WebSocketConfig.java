package smwu._back.configurer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import smwu._back.utils.ChatHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler") // 특정 URL에 웹소켓 핸들러를 매핑
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 핸드셰이크 요청을 인터셉트할 인터셉터 허용
                .withSockJS() //SocketJS 활성화
        ;

        // 특정 클라이언트의 Origin 요청만 허용하는 경우 (오리진의 모든 목록이나 특정목록을 허용하는 것도 가능)
        // registry.addHandler(myHandler(), "/myHandler").setAllowedOrigins("https://mydomain.com");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new ChatHandler();
    }

    //각 기본 WebSocket 엔진은 메시지 버퍼 크기, 유휴 시간 제한 등과 같은 런타임 특성을 제어하는 구성 속성을 노출함
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }




}
