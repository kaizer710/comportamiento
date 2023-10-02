/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Behaviours;
import Agentes.AgenteComprador;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author kaize
 */
public class solicitud extends Behaviour {
    
    private AID mejorVendedor;                // El agente vendedor con la mejor oferta
    private int mejorPrecio;                // El precio más bajo ofrecido
    private int repliesCount = 0;         // Contador de respuestas recibidas
    private MessageTemplate mt;           // Plantilla de mensajes
    private int step = 0;                 // Paso actual en el comportamiento
    private AgenteComprador bbAgent; // El agente comprador asociado
    private String tituloLibro;  
    
    public solicitud(AgenteComprador a) {
        bbAgent = a;
        tituloLibro = a.obtenerTituloLibro();
    }
    
        // Método de ejecución del comportamiento
    public void action() {
        switch (step) {
            case 0:
                // se envia solicitud a los vendedores
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < bbAgent.obtenerMejorVendedor().length; i++) {
                    cfp.addReceiver(bbAgent.obtenerMejorVendedor()[i]);
                }

                cfp.setContent(tituloLibro);
                cfp.setConversationId("book-trade");
                cfp.setReplyWith("cfp" + System.currentTimeMillis());
                myAgent.send(cfp);

                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                        MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                step = 1;
                break;

            case 1:
                // evaluando respuestas de los vendedores
                ACLMessage reply = bbAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.PROPOSE) {
                        int price = Integer.parseInt(reply.getContent());
                        if (mejorVendedor == null || price < mejorPrecio) {
                            mejorPrecio = price;
                            mejorVendedor = reply.getSender();
                        }
                    }
                    repliesCount++;
                    if (repliesCount >= bbAgent.obtenerMejorVendedor().length) {
                        step = 2;
                    }
                } else {
                    block();
                }
                break;

            case 2:
                // Paso 2: Enviar una orden de compra al agente vendedor con la mejor oferta
                ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                order.addReceiver(mejorVendedor);
                order.setContent(tituloLibro);
                order.setConversationId("book-trade");
                order.setReplyWith("order" + System.currentTimeMillis());
                bbAgent.send(order);

                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                        MessageTemplate.MatchInReplyTo(order.getReplyWith()));

                step = 3;
                break;

            case 3:
                // Paso 3: Recibir confirmación de compra o mensaje de falla
                reply = myAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.INFORM) {
                        System.out.println(tituloLibro + " comprado exitosamente al agente " + reply.getSender().getName());
                        System.out.println("Precio = " + mejorPrecio);
                        myAgent.doDelete(); // Terminar el agente comprador
                    } else {
                        System.out.println("El intento falló: ¡el libro solicitado ya se vendió!");
                    }
                    step = 4;
                } else {
                    block();
                }
                break;
        }
    }
    
      // finaliza el los comportramientos
    public boolean done() {
        if (step == 2 && mejorVendedor == null) {
            System.out.println("El intento falló: " + tituloLibro + " no está disponible para la venta");
        }
        return ((step == 2 && mejorVendedor == null) || step == 4);
    }
}
