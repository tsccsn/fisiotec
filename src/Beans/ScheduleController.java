
import cp.clinica.CPTratamento;
import dao.clinica.DaoTratamento;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.*;
import utilidades.data.UtilData;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thiago
 */
@ManagedBean(name="scheduleController")
@ViewScoped
public class ScheduleController implements Serializable{
    
    private ScheduleModel eventModel;  
      
    private ScheduleModel lazyEventModel;  
  
    private Timestamp inicio = UtilData.getDataTimestamp();
    private Timestamp termino= UtilData.getDataTimestamp();
    
    private ScheduleEvent event = new DefaultScheduleEvent();  
      
    private String theme;  
  
    public ScheduleController() {
        eventModel = new DefaultScheduleModel();
        List<CPTratamento> todosOsTratamentos = DaoTratamento.getAll();
        for (CPTratamento xTratamento : todosOsTratamentos) {
            eventModel.addEvent(new DefaultScheduleEvent(xTratamento.getSessao().getPaciente().getNome()+" - "+xTratamento.getBox()+" - "+xTratamento.getObs(),
                    xTratamento.getDia(),xTratamento.getDia()));
        }
        lazyEventModel = new LazyScheduleModel() {  
            public void fetchEvents(Date start, Date end) {  
                Date random = new Date(UtilData.converteEmDate("16/12/2011").getTime()); 
                addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));  
                random = new Date(UtilData.converteEmDate("21/12/2011").getTime());  
                addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));  
            }     
        };  
    }  
      
    public void addEvent() {  
         eventModel.addEvent(event);
    }  
      
    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {  
        event = selectEvent.getScheduleEvent();  
    }  
      
    public void onDateSelect(DateSelectEvent selectEvent) {  
        event = new DefaultScheduleEvent(Math.random() + "TRETA", selectEvent.getDate(), selectEvent.getDate());  
        inicio.setTime(selectEvent.getDate().getTime());
        termino.setTime(selectEvent.getDate().getTime());
        System.out.println("on date select data: "+ UtilData.diaNmesNanoN(inicio));
        System.out.println("on date select data: "+ UtilData.diaNmesNanoN(termino));
    }  
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public Timestamp getTermino() {
        return termino;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public void setTermino(Timestamp termino) {
        this.termino = termino;
    }
    
}
