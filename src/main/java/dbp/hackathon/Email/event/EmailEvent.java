package dbp.hackathon.Email.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class EmailEvent extends ApplicationEvent {
    private final String email;

    private final String content;

    private final String subject;

    public EmailEvent(String email, String content, String subject) {
        super(email);
        this.email = email;
        this.content = content;
        this.subject = subject;
    }
}