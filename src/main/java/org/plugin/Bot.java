//package org.plugin;
//
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//public class Bot extends TelegramLongPollingBot {
//    @Override
//    public String getBotUsername() {
//        return "GradleSenderResultsBot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "5979849534:AAF4WfbQUzehAGcLFY4ptcXukVb4iyLwFyA";
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        var msg = update.getMessage();
//        var user = msg.getFrom();
//        System.out.println(user.getId() + " wrote " + msg.getText());
////        sendText(user.getId(), "Hey, You, chert@");
//    }
//
//    public void sendText(Long who, String q){
//        SendMessage sm = SendMessage.builder()
//                .chatId(who.toString()) //Who are we sending a message to
//                .text(q).build();    //Message content
//        try {
//            execute(sm);                        //Actually sending the message
//        } catch (TelegramApiException qr) {
//            throw new RuntimeException(qr);      //Any error will be printed here
//        }
//    }
//}
