package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Bot extends TelegramLongPollingBot {

    int hideM = 0;
    int hideB = 0;

    // text to bot
    String helloMessage = "Привіт, тебе вітає Бот Підслухано ЕТІ. Тут ти можеш надіслати анонімно своє повідомлення (або ж не анонімно)";
    String hideMessage = "Надішліть своє анонімне повідомлення";
    String Message = "Надішліть своє повідомлення";

    String adminID = "5308141619";
    String oldChatID = "";
    String oldMessage = "";
    String Name;
    String Username;
    String groupID = "-1002079851465";

    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
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
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            handleMessage(update.getMessage());
            if (update.hasMessage() && hideM == 1) {
                System.out.println("Send message to me with bot");
                execute(
                        SendMessage
                                .builder()
                                .chatId(adminID)
                                .text(update.getMessage().getText())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build()
                );
                hideM = 0;
            } else if (update.hasMessage() && hideM == 2) {
                System.out.println("send message don`t anonim");
                execute(SendMessage
                        .builder()
                        .text(update.getMessage().getText())
                        .chatId(adminID)
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                        .build()
                );
                oldMessage = update.getMessage().getText();
                hideM = 0;
                hideB = 1;
            }
        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        System.out.println("handlecallback");

        Message message = (Message) callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        String action = param[0];



        switch (action) {
            case ("HideMessage"):
                execute(
                        SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text(hideMessage)
                                .build());
                System.out.println("send bot message");
                oldChatID = message.getChatId().toString();
                hideM = 1;
                break;
            case ("Message"):
                System.out.println("Message");
                execute(
                        SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text(Message)
                                .build());
                oldChatID = message.getChatId().toString();
                hideM = 2;
                break;
            case ("Post"):
                System.out.println("post Message");
                if (hideB == 1) {
                    if (Username != null) {
                        execute(SendMessage
                                .builder()
                                .chatId(groupID)
                                .text("Повідомлення від - " + Name + " (@" + Username + ")\n" + oldMessage)
                                .build());
                    } else {
                        execute(SendMessage
                                .builder()
                                .chatId(groupID)
                                .text("Повідомлення від - " + Name + "\n" + oldMessage)
                                .build());
                    }
                    hideB = 0;
                } else {
                    execute(
                            SendMessage
                                    .builder()
                                    .chatId(groupID)
                                    .text(message.getText())
                                    .build());
                }
                break;
            case ("Don`t post"):
                System.out.println("don`t post message");
                execute(SendMessage
                        .builder()
                        .text("Нажаль не можемо пропустити ваше повідомлення :(")
                        .chatId(oldChatID)
                        .build());
                break;

        }
    }

    @SneakyThrows
    private void handleMessage(Message message) {
        System.out.println("handleMessage");
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
                        Name = message.getFrom().getFirstName();
                        Username = message.getFrom().getUserName();
                        System.out.println("case command");
                        execute(
                                SendMessage
                                        .builder()
                                        .chatId(message.getChatId())
                                        .text(helloMessage)
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build()
                        );
                        break;
                }
            }
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
