package ru.easyUm.mainpackage.commandlinerunners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.easyUm.Messager;
import ru.easyUm.MessagerConfig;

import java.util.Arrays;


@Component
public class PreparationDev implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PreparationDev.class);

    private final Messager messager;

    public PreparationDev(Messager messager) {
        this.messager = messager;
    }

    public PreparationDev() {
        MessagerConfig messagerConfig = new MessagerConfig("Hello from Messager EasyUm Java");
        this.messager = new Messager(messagerConfig);
    }

    @Override
    public void run(String... args) {
        logger.info("DEV mode!!! Что-то настравиваем и подготавливаем, параметры: {} ", Arrays.toString(args));
        logger.info("message from Messager:{}", messager.sayMessage());
        //args парметры, котрые могут быть переданы в Main
    }
}
