package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Handle extends TgBot {
    @SneakyThrows
    public void handleMessage(Message message) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton
                        .builder()
                        .text("Анонімне повідомлення")
                        .callbackData("HideMessage:")
                        .build(),
                InlineKeyboardButton
                        .builder()
                        .text("Не анонімне повідомлення")
                        .callbackData("Message:")
                        .build()
        ));
        if (message.hasText()) {
            execute(
                    SendMessage.builder()
                            .chatId(message.getChatId())
                            .text("Як саме бажаєте запостити повідомлення?")
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        }
    }

    @SneakyThrows
    public void handleCallBack(CallbackQuery callbackQuery) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton
                        .builder()
                        .text("Пост")
                        .callbackData("Post:")
                        .build(),
                InlineKeyboardButton
                        .builder()
                        .text("Не постить")
                        .callbackData("Don`t post:")
                        .build()
        ));
        MaybeInaccessibleMessage message = callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        String action = param[0];
        switch (action) {
            case ("HideMessage") :
                execute(SendMessage
                        .builder()
                        .chatId("-834911490")
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                        .text(message.getChatId().toString())
                        .build());
                break;
            case ("Post"):
                execute(SendMessage
                        .builder()
                        .chatId("1002112191429")
                        .text(callbackQuery.getMessage().toString())
                        .build());
                break;
        }
    }
}
