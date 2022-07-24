package smwu._back.controller;

        import lombok.RequiredArgsConstructor;
        import org.springframework.messaging.handler.annotation.MessageMapping;
        import org.springframework.messaging.handler.annotation.SendTo;
        import org.springframework.web.bind.annotation.*;
        import smwu._back.domain.Chat;
        import smwu._back.domain.ChatContent;
        import smwu._back.repository.ChatContentRepository;
        import smwu._back.repository.ChatRepository;
//        import smwu._back.chat.service.ChatService;
        import smwu._back.repository.FindUserRepository;
        import smwu._back.domain.User;

        import java.time.LocalDateTime;
        import java.util.*;
        import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;
    private final ChatContentRepository chatContentRepository;
    private final FindUserRepository findUserRepository;

    @MessageMapping("/chat")
    @SendTo("")


    //번호 : [{ "who":"me"/"you", "time":time, "message":message }]
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/chat")
    public LinkedHashMap<Integer, HashMap<String,String> > chatMyMessages (@RequestBody Map<Object, String> userinfo){

        Long post_key = Long.valueOf(userinfo.get("POST_KEY"));


        String SENDER_ID =userinfo.get("SENDER_ID").toString();
        System.out.println(SENDER_ID);
        User me = findUserRepository.finduserWithID(SENDER_ID);
        System.out.println(me);
        String RECEIVER_ID =userinfo.get("RECEIVER_ID").toString();
        User receiver = findUserRepository.finduserWithID(RECEIVER_ID);

        LinkedHashMap<Integer, HashMap<String,String> > messages = new LinkedHashMap<>();


        if(me==null){
            System.out.println("no id");
        }
        else{
            System.out.println(me.getKeypid()+me.getId());
            System.out.println(receiver.getKeypid()+receiver.getId());
        }

        if( chatRepository.getUserChatList(me.getKeypid().intValue(), receiver.getId(), post_key ).size()==0){
            final Chat chatting_my =Chat.builder().user(me).receiver_id(receiver.getId()).post_key(post_key).build();
            chatRepository.save(chatting_my);
            final Chat chatting_receiver =Chat.builder().user(receiver).receiver_id(me.getId()).post_key(post_key).build();
            chatRepository.save(chatting_receiver);
        }
        else{
            Chat mychat=chatRepository.getUserChatList(me.getKeypid(), receiver.getId(),  post_key).get(0);
            Chat receiverchat=chatRepository.getUserChatList(receiver.getKeypid(), me.getId(), post_key).get(0);

            List<ChatContent> allMessage = chatContentRepository.getAllMessage(mychat.getChat_key(), post_key);
            AtomicInteger count= new AtomicInteger();
            allMessage.forEach(message-> {
                if(message.getSender_id().equals(me.getId())){
                    HashMap<String,String> temp=new HashMap<>();
                    temp.put("who","me");
                    temp.put("message",message.getChat_message());
                    temp.put("time", message.getChat_date().toString());
                    messages.put(count.getAndIncrement(),temp);
                }
                else{
                    HashMap<String,String> temp=new HashMap<>();
                    temp.put("who","you");
                    temp.put("message",message.getChat_message());
                    temp.put("time", message.getChat_date().toString());
                    messages.put(count.getAndIncrement(),temp);
                }
            });
        }

        return messages;
//        return ChatService.chattingPageMessages(post_key, me, receiver);

    }



    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/chat/message")
    public void chatFindUser (@RequestBody Map<Object, String> userinfo){

        String INPUT_ID = userinfo.get("INPUT_ID").toString();
        System.out.println(INPUT_ID);
        String RECEIVER_ID =userinfo.get("RECEIVER_ID").toString();
        String MY_MESSAGE =userinfo.get("MY_MESSAGE").toString();
        Long POST_KEY = Long.valueOf(userinfo.get("POST_KEY"));

//        ChatService.putNewChatMessage(INPUT_ID, RECEIVER_ID, MY_MESSAGE, POST_KEY);
        if(findUserRepository.finduserWithID(INPUT_ID)==null) {
            System.out.println("null");
        }
        User me = findUserRepository.finduserWithID(INPUT_ID);
        User receiver = findUserRepository.finduserWithID(RECEIVER_ID);
        if(me==null){
            System.out.println("no id");
        }
        else{
            System.out.println(me.getKeypid()+me.getId());
            System.out.println(receiver.getKeypid()+receiver.getId());
        }


        if( chatRepository.getUserChatList(me.getKeypid().intValue(), receiver.getId(), POST_KEY ).size()==0){

            final Chat chatting_my =Chat.builder().user(me).receiver_id(receiver.getId()).post_key(POST_KEY).build();
            chatRepository.save(chatting_my);

            final ChatContent chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(chatting_my).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
            chatContentRepository.save(chatting_message);


            final Chat chatting_receiver =Chat.builder().user(receiver).receiver_id(me.getId()).post_key(POST_KEY).build();
            chatRepository.save(chatting_receiver);

            final ChatContent chatting_messages = ChatContent.builder().chat_message(MY_MESSAGE).chat(chatting_receiver).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
            chatContentRepository.save(chatting_messages);



            System.out.println("1성공");

        }

        else {
            Chat mychat=chatRepository.getUserChatList(me.getKeypid(), receiver.getId(), POST_KEY).get(0);
            Chat receiverchat=chatRepository.getUserChatList(receiver.getKeypid(), me.getId(), POST_KEY).get(0);

            final ChatContent chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(mychat).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
            chatContentRepository.save(chatting_message);

            final ChatContent receiver_chatting_message = ChatContent.builder().chat_message(MY_MESSAGE).chat(receiverchat).chat_date(LocalDateTime.now()).sender_id(INPUT_ID).post_key(POST_KEY).build();
            chatContentRepository.save(receiver_chatting_message);

            System.out.println("3성공!!!");

        }

    }

    // 사용자 keypid로 채팅chat_key 찾기 -> 채팅 컨텐츠에서 chat_key로 채팅의 post_key 리스트 찾기-> 포스트 제목, 상대 이름, 마지막 채팅 날짜, 마지막 채팅 메시지 프론트로 전송
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/myinfo/chatlist")
    public LinkedHashMap<Integer, HashMap<String,String> > MyChattingList(@RequestBody Map<Object, String> userinfo){

        if(userinfo==null){
            LinkedHashMap<Integer, HashMap<String,String>> noInfofromFront =new LinkedHashMap<>();
            HashMap<String,String> noInfo = new HashMap<>();
            noInfo.put("nouser","nouser");
            noInfofromFront.put(0,noInfo);
            return noInfofromFront;
        }

        User me = findUserRepository.finduserWithID(userinfo.get("MY_ID").toString());
        List<Chat> myAllChat = chatRepository.getAllChatListwithKEY(me.getKeypid());
        System.out.println("myAllchat:"+myAllChat);

        LinkedList<Long> findMyChatPostsKEYS = new LinkedList<>();
        LinkedList<String> finalMessage = new LinkedList<>();
        LinkedList<LocalDateTime> finalTime = new LinkedList<>();
        LinkedList<String > receiver = new LinkedList<>();

        myAllChat.forEach(chat -> {
            List<ChatContent> chatContents = chatContentRepository.findMyAllChatContent(chat.getChat_key());

            final LocalDateTime[] forCompareTime = {LocalDateTime.of(2020, 6, 1, 0, 0, 0)};
            final String[] tmpMessage = {""};
            final String[] tmpReceiver = {""};
            final Long[] tmpPostkey={Long.valueOf(0)};



            chatContents.forEach(chatContent -> {
                if(chatContent.getChat_date().isAfter(forCompareTime[0])){
                    tmpMessage[0] =chatContent.getChat_message();
                    forCompareTime[0] =chatContent.getChat_date();
                }
            });


            if(forCompareTime[0].equals(LocalDateTime.of(2020, 6, 1, 0, 0, 0))){

            }
            else {
                findMyChatPostsKEYS.add(chat.getPost_key());
                finalMessage.add(tmpMessage[0]);
                finalTime.add(forCompareTime[0]);
                receiver.add(chat.getReceiver_id());

                for (int i = finalTime.size() - 2; i > 0; i--) {

                    if (finalTime.get(i).isAfter(finalTime.get(i + 1))) {
                    } else {

                        forCompareTime[0] = finalTime.get(i);
                        finalTime.set(i, finalTime.get(i + 1));
                        finalTime.set(i + 1, forCompareTime[0]);

                        tmpMessage[0] = finalMessage.get(i);
                        finalMessage.set(i, finalMessage.get(i + 1));
                        finalMessage.set(i + 1, tmpMessage[0]);

                        tmpReceiver[0] = receiver.get(i);
                        receiver.set(i, receiver.get(i + 1));
                        receiver.set(i + 1, tmpReceiver[0]);

                        tmpPostkey[0] = findMyChatPostsKEYS.get(i);
                        findMyChatPostsKEYS.set(i, findMyChatPostsKEYS.get(i + 1));
                        findMyChatPostsKEYS.set(i + 1, tmpPostkey[0]);

                    }
                }
            }

        });




        LinkedHashMap<Integer, HashMap<String,String> > myAllchattings= new LinkedHashMap<>();

        final int[] count = {0};
        findMyChatPostsKEYS.forEach(post->{
            count[0] = count[0] +1;
            HashMap<String, String> temp =new HashMap<>();
            temp.put("finalmessage", finalMessage.get(findMyChatPostsKEYS.indexOf(post)));
            temp.put("finaltime", finalTime.get(findMyChatPostsKEYS.indexOf(post)).toString());
            temp.put("receiver", receiver.get(findMyChatPostsKEYS.indexOf(post)));
            temp.put("postnum", String.valueOf(post));
            myAllchattings.put(count[0], temp);

        });

        if(count[0]==0){
            HashMap<String, String> temp =new HashMap<>();
            temp.put("no","no");
            myAllchattings.put(0, temp);
        }


        return myAllchattings;

    }

}