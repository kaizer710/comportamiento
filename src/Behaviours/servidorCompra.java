/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Behaviours;
/*
import Agentes.AgenteVendedorL;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author kaize
 
public class servidorCompra extends CyclicBehaviour{
    private AgenteVendedorL bsAgent;
    
    //constructor del comportamiento 
    public servidorCompra(AgenteVendedorL a){
        bsAgent = a;
    }
    
    // creaciòn metodo del comportamiento
    
    public void action(){
        //platilla para capturar los mensajes de propuestas
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        // recibir el mensaje que cumple con la solicitud
        ACLMessage msg = bsAgent.receive(mt);
        
        if (msg != null){
            String titulo = msg.getContent();
            ACLMessage reply = msg.createReply();
            
            //obteniendo precio de libro 
            Integer price = (Integer) bsAgent.obtenerCatalogo().remove(titulo);
            
            if (price != null){
                //si esta libro en catalogo mostrar que fue vendido
                reply.setPerformative(ACLMessage.INFORM);
                System.out.println(titulo + " vendido al agente " + msg.getSender().getName());
            }else {
                // se muestra mensaje que libro no esta disponible
                reply.setPerformative(ACLMessage.FAILURE);
                reply.setContent("Libro no disponible");
            }
            
            //se mandan atributos al agente
            bsAgent.send(reply);
        } else {
            block(); // se pausa accion hasta esperar nueva propuesta
        }
    }
}

*/



import Agentes.AgenteVendedorL;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class servidorCompra extends CyclicBehaviour {
    private AgenteVendedorL bsAgent;

    // Constructor del comportamiento
    public servidorCompra(AgenteVendedorL a) {
        bsAgent = a;
    }

    // Método principal del comportamiento
    public void action() {
        // Definir una plantilla de mensaje para capturar mensajes de tipo ACCEPT_PROPOSAL
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

        // Recibir el mensaje que cumple con la plantilla
        ACLMessage msg = bsAgent.receive(mt);

        if (msg != null) {
            String title = msg.getContent();
            ACLMessage reply = msg.createReply(); // Crear una respuesta al mensaje recibido

            // Intentar obtener el precio del libro del catálogo del agente vendedor
            Integer price = (Integer) bsAgent.getCatalogue().remove(title);

            if (price != null) {
                // Si el libro está disponible en el catálogo, informar sobre la compra exitosa
                reply.setPerformative(ACLMessage.INFORM);
                System.out.println(title + " vendido al agente " + msg.getSender().getName());
            } else {
                // Si el libro no está disponible en el catálogo, informar sobre la falta de disponibilidad
                reply.setPerformative(ACLMessage.FAILURE);
                reply.setContent("no-disponible");
            }

            // Enviar la respuesta al agente comprador
            bsAgent.send(reply);
        } else {
            block(); // Bloquear el comportamiento si no se recibe ningún mensaje
        }
    }
}

