package hu.tsystems.msstools.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    private TableStatProviderService tableStatProviderService;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() throws Exception {
        log.info("The time is now {}", dateFormat.format(new Date()));
        
        tableStatProviderService.updateAll();
    }
}
