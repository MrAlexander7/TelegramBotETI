package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TgBot extends TelegramLongPollingBot {
    private static final String botName = "@ETIStudentBot";
    private static final String botToken = "6860262859:AAEQiOxTCcoeqSyYRYub6mCgV5WODI4buQs";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Handle handle = new Handle();
            handle.handleMessage(update.getMessage());
            if (update.hasCallbackQuery()) {
                handle.handleCallBack(update.getCallbackQuery());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
