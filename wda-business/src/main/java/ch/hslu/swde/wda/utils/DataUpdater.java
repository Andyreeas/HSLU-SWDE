package ch.hslu.swde.wda.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataUpdater implements CommandLineRunner  {

    @Autowired
    ScheduledTask scheduledTask;

    @Override
    public void run(String... args) throws Exception {
        scheduledTask.startUpdatingData();
    }
}
