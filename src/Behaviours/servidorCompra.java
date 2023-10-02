/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Behaviours;
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

