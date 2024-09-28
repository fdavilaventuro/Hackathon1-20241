package dbp.hackathon.Ticket;

import dbp.hackathon.Email.event.EmailEvent;
import dbp.hackathon.Estudiante.Estudiante;
import dbp.hackathon.Estudiante.EstudianteRepository;
import dbp.hackathon.Funcion.Funcion;
import dbp.hackathon.Funcion.FuncionRepository;
import dbp.hackathon.QR.QRHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private FuncionRepository funcionRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TemplateEngine templateEngine;

    public Ticket createTicket(Long estudianteId, Long funcionId, Integer cantidad) {
        QRHandler qrHandler = new QRHandler();
        Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);
        Funcion funcion = funcionRepository.findById(funcionId).orElse(null);
        if (estudiante == null || funcion == null) {
            throw new IllegalStateException("Estudiante or Funcion not found!");
        }

        Ticket ticket = new Ticket();
        ticket.setEstudiante(estudiante);
        ticket.setFuncion(funcion);
        ticket.setCantidad(cantidad);
        ticket.setEstado(Estado.VENDIDO);
        ticket.setFechaCompra(LocalDateTime.now());
        ticket.setQr(qrHandler.getSaltString());

        Context context = new Context();
        context.setVariable("nombre", estudiante.getName());
        context.setVariable("nombrePelicula", funcion.getNombre());
        context.setVariable("fechaFuncion", funcion.getFecha());
        context.setVariable("cantidad", cantidad);
        context.setVariable("precioTotal", cantidad*funcion.getPrecio());
        context.setVariable("qr", ticket.getQr());

        final String htmlContent = templateEngine.process("EmailTemplate.html", context);
        

        eventPublisher.publishEvent(new EmailEvent(estudiante.getEmail(), htmlContent, "Tu entrada ha sido adquirida con exito"));

        return ticketRepository.save(ticket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Iterable<Ticket> findByEstudianteId(Long estudianteId) {
        return ticketRepository.findByEstudianteId(estudianteId);
    }

    public void changeState(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            throw new IllegalStateException("Ticket not found!");
        }
        ticket.setEstado(Estado.CANJEADO);
        ticketRepository.save(ticket);
    }

    public Boolean validateTicket(Long id, String qr) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new IllegalStateException("Ticket not found!"));

        return ticket.getQr().equals(qr);
    }
}
