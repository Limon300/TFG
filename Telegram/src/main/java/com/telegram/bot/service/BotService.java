/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.telegram.bot.service;

import com.telegram.bot.dto.Registro;
import com.telegram.bot.dto.TelegramRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 *
 * @author usuario
 */
@Component
public class BotService extends AbilityBot{
    
    @Autowired
    SecurityService servicioSeguridad;
    
    private final ResponseHandler responseHandler;
    
    public ResponseEntity Notificar(TelegramRequest peticion) {
        SendMessage message = new SendMessage();
        message.setChatId(peticion.getChat_id());
        message.setText(peticion.getMensaje());
        try {
            sender.execute(message);
            return ResponseEntity.ok().build();
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotService.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.internalServerError().build();
        }
    }
    


    @Autowired
    //env.getProperty("botToken")
    public BotService(Environment env) {
        super(env.getProperty("botToken"),env.getProperty("botName"));
        responseHandler = new ResponseHandler(silent);
    }
    
    @Override
    public long creatorId() {
        return 1L;
    }
    
    public Ability startBot() {
        return Ability
          .builder()
          .name("start")
          .info("Inicio")
          .locality(USER)
          .privacy(PUBLIC)
          .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
          .build();
    }
    
    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) -> responseHandler.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT,upd -> responseHandler.userIsActive(getChatId(upd)));
    }

    
    
    public class ResponseHandler {
        private final SilentSender sender;
        private final Map<Long, Registro> chatStates;
        
        public ResponseHandler(SilentSender sender) {
            this.sender = sender;
            chatStates = new HashMap();
        }

        private void replyToStart(Long chatId) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Hola, para que puedas recibir informacion de tus agentes, introduce tu id");
            sender.execute(message);
            chatStates.put(chatId, new Registro("ESPERANDO_ID",chatId));
        }

        private void replyToButtons(Long chatId, Message message) {
            if (message.getText().equalsIgnoreCase("/stop")) {
                stopChat(chatId);
            }

            switch (chatStates.get(chatId).getEstado()) {
                case "ESPERANDO_ID" -> replyToID(chatId, message);
                case "ESPERANDO_PASS" -> replyToPASS(chatId, message);
                default -> unexpectedMessage(chatId);
            }
        }
        
        private void replyToID(Long chatId,Message message) {
            SendMessage replymessage = new SendMessage();
            replymessage.setChatId(chatId);
            replymessage.setText("Ahora envia tu token");
            sender.execute(replymessage);
            Registro nuevo = new Registro("ESPERANDO_PASS",chatId);
            nuevo.setId_usuario(Long.valueOf(message.getText()));
            chatStates.put(chatId, nuevo);
        }
        
        private void replyToPASS(Long chatId,Message message) {
            SendMessage replymessage = new SendMessage();
            replymessage.setChatId(chatId);
            if(servicioSeguridad.checkUser(chatStates.get(chatId), message.getText())){
                replymessage.setText("Gracias, se registro correctamente su telegram");
            }
            else{
                replymessage.setText("Error, intentelo de nuevo");
            }
            
            sender.execute(replymessage);
            chatStates.remove(chatId);
        }
        
        private void unexpectedMessage(long chatId) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Error");
            sender.execute(sendMessage);
        }
        
        public boolean userIsActive(Long chatId) {
            return chatStates.containsKey(chatId);
        }
        
        private void stopChat(long chatId) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Gracias");
            chatStates.remove(chatId);
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            sender.execute(sendMessage);
        }
        

    }
    
}
