package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Bot extends TelegramLongPollingBot {

    // text to bot
    String helloMessage = "Привіт, тебе вітає Бот Підслухано ЕТІ. Тут ти можеш надіслати анонімно своє повідомлення (або ж не анонімно)";
    String hideMessage = "Надішліть своє анонімне повідомлення";
    String Message = "Надішліть своє повідомлення";

    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
            /*Message message = update.getMessage();
            if (message.hasText() || message.hasAudio() || message.hasPhoto()) {
                execute(SendMessage.builder().chatId(message.getChatId().toString()).text(message.getText()).build());
                execute(new CopyMessage("-1002079851465" ,message.getChatId().toString(),message.getMessageId()));
            */
        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = (Message) callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(" ");
        String action = param[0];
        switch (action) {
            case ("HideMessage"):
                execute(
                        SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text(hideMessage)
                                .build());
                /*execute(
                        new CopyMessage(
                                "-1002112191429"
                                , message.getChatId().toString()
                                , message.getMessageId()));*/
        }
    }

    @SneakyThrows
    private void handleMessage(Message message) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton
                        .builder()
                        .text("Анонімне повідомлення")
                        .callbackData("HideMessage")
                        .build(),
                InlineKeyboardButton
                        .builder()
                        .text("Не анонімне повідомлення")
                        .callbackData("Message")
                        .build()
        ));
        // handle message
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case ("/start"), ("/next"):
                        execute(
                                SendMessage.builder()
                                        .chatId(message.getChatId())
                                        .text(helloMessage)
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build());
                        return;
                }
            }
        } else if (message.hasText()) {
            execute(new CopyMessage("-1002112191429" ,message.getChatId().toString(),message.getMessageId()));
        }
    }

    @Override
    public String getBotUsername() {
        return "@ETIStudentBot";
    }

    @Override
    public String getBotToken() {
        return "6860262859:AAEQiOxTCcoeqSyYRYub6mCgV5WODI4buQs";
    }
}
