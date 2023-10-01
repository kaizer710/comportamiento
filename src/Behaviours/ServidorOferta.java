/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Behaviours;

import Agentes.AgenteVendedorL;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author kaize
 */
public class ServidorOferta extends CyclicBehaviour{
    private AgenteVendedorL bsAgent;
    
    //constructor de comportamiento
    public ServidorOferta(AgenteVendedorL a){
        bsAgent = a;
    }
public void action(){
    //pantalla para catulrar mensajes Call for Proposal
    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    // Recibir el mensaje que cumple con la pantalla
    
    ACLMessage msg = bsAgent.receive(mt);
    
    if(msg != null){
        String title=msg.getContent();
        ACLMessage reply = msg.createReply(); // crea una respuesta al mensaje recibido
        
        //intenta obtener precio del libro dentro del catalogo agente vendedor
        Integer price = (Integer) bsAgent.getCatalogue().get(title); //error porque no esta definido catalogo vendedor
        
        if (price != null){
            //si el libro esta disponible se propone precio a comprador
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(String.valueOf(price.intValue()));
        }else {
            //si el libro no esta disponible se rechaza la solicitud
            reply.setPerformative(ACLMessage.REFUSE);
            reply.setContent("Libro no disponible");
            
        }
        
        //se evia respuesta al comprador
        bsAgent.send(reply);
        
    } else {
        block (); //este metodo se utiliza para pausar actividad de agente
    }
    
}    
    
}
