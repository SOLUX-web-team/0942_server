//package smwu._back.chat.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import smwu._back.chat.domain.Chat;
//import smwu._back.chat.domain.ChatContent;
//import smwu._back.chat.repository.ChatContentRepository;
//import smwu._back.chat.repository.ChatRepository;
//import smwu._back.users.domain.UserInfoVO;
//import smwu._back.users.repository.FindUserRepository;
//import smwu._back.users.repository.LoginRepository;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Transactional
//@Service
//@RequiredArgsConstructor
//public class ChatService {
//
//    private static FindUserRepository findUserRepository;
//    private static ChatRepository chatRepository;
//    private static ChatContentRepository chatContentRepository;
//    private static LoginRepository loginRepository;
//
//
//    public static LinkedHashMap<Integer, HashMap<String,String>> chattingPageMessages(Long post_key, UserInfoVO me, UserInfoVO receiver){
//
////        UserInfoVO me = findUserRepository.finduserWithID(SENDER_ID);
////        UserInfoVO receiver = findUserRepository.finduserWithID(RECEIVER_ID);
//
//        LinkedHashMap<Integer, HashMap<String,String> > messages = new LinkedHashMap<>();
//
//
//        if(me==null){
//            System.out.println("no id");
//        }
//        else{
//            System.out.println(me.getKeypid()+me.getId());
//            System.out.println(receiver.getKeypid()+receiver.getId());
//        }
//
//        if( chatRepository.getUserChatList(me.getKeypid().intValue(), receiver.getId() ).size()==0){
//            final Chat chatting_my =Chat.builder().user(me).receiver_id(receiver.getId()).build();
//            chatRepository.save(chatting_my);
//            final Chat chatting_receiver =Chat.builder().user(receiver).receiver_id(me.getId()).build();
//            chatRepository.save(chatting_receiver);
//        }
//        else{
//            Chat mychat=chatRepository.getUserChatList(me.getKeypid(), receiver.getId()).get(0);
//            Chat receiverchat=chatRepository.getUserChatList(receiver.getKeypid(), me.getId()).get(0);
//
//            List<ChatContent> allMessage = chatContentRepository.getAllMessage(mychat.getChat_key(), post_key);
//            AtomicInteger count= new AtomicInteger();
//            allMessage.forEach(message-> {
//                if(message.getSender_id().equals(me.getId())){
//                    HashMap<String,String> temp=new HashMap<>();
//                    temp.put("who","me");
//                    temp.put("message",message.getChat_message());
//                    temp.put("time", message.getChat_date().toString());
//                    messages.put(count.getAndIncrement(),temp);
//                }
//                else{
//                    HashMap<String,String> temp=new HashMap<>();
//                    temp.put("who","you");
//                    temp.put("message",message.getChat_message());
//                    temp.put("time", message.getChat_date().toString());
//                    messages.put(count.getAndIncrement(),temp);
//                }
//            });
//        }
//
//        return messages;
//    }
//
//
//    public  static void putNewChatMessage(String INPUT_ID, String RECEIVER_ID, String MY_MESSAGE, Long POST_KEY){
//
//        if(findUserRepository.finduserWithID(INPUT_ID)==null) {
//            System.out.println("null");
//        }
//        UserInfoVO me = findUserRepository.finduserWithID(INPUT_ID);
//        UserInfoVO receiver = findUserRepository.finduserWithID(RECEIVER_ID);
//        if(me==null){
//            System.out.println("no id");
//        }
//        else{
//            System.out.println(me.getKeypid()+me.getId());
//            System.out.println(receiver.getKeypid()+receiver.getId());
//        }
//
//
//        if( chatRepository.getUserChatList(me.getKeypid().intValue(), receiver.getId() ).size()==0){
//
//            final Chat chatting_my =Chat.builder().user(me).receiver_id(receiver.getId()).build();
//            chatRepository.save(chatting_my);
//
//            final ChatContent chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(chatting_my).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
//            chatContentRepository.save(chatting_message);
//
//
//
//            final Chat chatting_receiver =Chat.builder().user(receiver).receiver_id(me.getId()).build();
//            chatRepository.save(chatting_receiver);
//
//            final ChatContent chatting_messages = ChatContent.builder().chat_message(MY_MESSAGE).chat(chatting_receiver).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
//            chatContentRepository.save(chatting_messages);
//
//
//
//            System.out.println("1성공");
//
//        }
//
//        else {
//            Chat mychat=chatRepository.getUserChatList(me.getKeypid(), receiver.getId()).get(0);
//            Chat receiverchat=chatRepository.getUserChatList(receiver.getKeypid(), me.getId()).get(0);
//
//            final ChatContent chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(mychat).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
//            chatContentRepository.save(chatting_message);
//
//            final ChatContent receiver_chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(receiverchat).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
//            chatContentRepository.save(receiver_chatting_message);
//
//            System.out.println("3성공!!!");
//
//        }
//    }
//}
