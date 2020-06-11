package Planet_Bound.logica.dados.events;


/**<h1>Eventos</h1>
 * Este é o meu conceito de logging.
 *
 * Os eventos sao formados por um
 *  -\link Planet_Bound.logica.dados.events.EventType EventType \endlink, para diferenciar os tipos de eventos;
 *  - uma mensagem personalizada, para o utilizador ler;
 *  - e um unix time stamp, para saber quando o evento foi registado.
 *
 */
public class Event {
    final EventType eventType;
    final String msg;
    final long unixTimewithMilisecs;

    public Event(EventType eventType, String msg) {
        this.eventType = eventType;
        this.msg = msg;
        unixTimewithMilisecs = System.currentTimeMillis();
    }

    public EventType getEventType() {
        return eventType;
    }

    public long getTimeWithoutMilisecs(){
        return unixTimewithMilisecs / 1000L;
    }
    public long getUnixTimewithMilisecs(){
        return unixTimewithMilisecs;
    }
    @Override
    public String toString() {
        return "[" + eventType.name() + "] at "+
                (new java.util.Date((long)unixTimewithMilisecs)).toString()
                + " : "+ msg;
    }
}
