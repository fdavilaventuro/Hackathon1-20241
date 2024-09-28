package dbp.hackathon.Email.event;


import dbp.hackathon.Email.domain.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
    @Autowired
    private EmailService emailService;

    @org.springframework.context.event.EventListener
    @Async
    public void handleHelloEmailEvent(EmailEvent event){
        emailService.sendMesage(event.getEmail(),event.getSubject(),event.getContent());
    }
}