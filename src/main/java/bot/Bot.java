package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            /*Message message = update.getMessage();
            *//*if (message.hasText() || message.hasAudio() || message.hasPhoto()) {
                execute(SendMessage.builder().chatId(message.getChatId().toString()).text(message.getText()).build());
                execute(new CopyMessage("-1002079851465" ,message.getChatId().toString(),message.getMessageId()));
            }*//*
            if (message.hasText()) {
                execute(new CopyMessage("-1002112191429", message.getChatId().toString(), message.getMessageId()));
                sendInlineKeyboard("-1002112191429");
                if (update.hasCallbackQuery()) {
                    String callbackQuery = update.getCallbackQuery().getData();
                    if (callbackQuery.equals("Yes")) {
                        execute(new CopyMessage("-1002079851465" ,message.getChatId().toString(),message.getMessageId()));
                    }
                }
            }*/
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
