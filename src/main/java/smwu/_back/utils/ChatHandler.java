package smwu._back.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

    // Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String user_name = searchUserName(session);
//        for(WebSocketSession sess : sessionList) {
//            sess.sendMessage(new TextMessage(user_name+"님이 접속했습니다."));
//        }
        sessionList.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String user_name= searchUserName(session);

        for(WebSocketSession sess: sessionList) {
            sess.sendMessage(new TextMessage(user_name+": "+message.getPayload()));
            log.info("payload:  "+message.getPayload());
        }
    }

    // Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String user_name = searchUserName(session);
        System.out.println("연결 끊어짐");
        for(WebSocketSession sess : sessionList) {
            sess.sendMessage(new TextMessage(user_name+"님의 연결이 끊어졌습니다."));
        }
        sessionList.remove(session);
    }

    public String searchUserName(WebSocketSession session)throws Exception {
        String user_name;
        Map<String, Object> map;
        map = session.getAttributes();
        user_name = (String) map.get("user_name");
        return user_name;
    }
}
