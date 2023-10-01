/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agentes;

import Behaviours.solicitud;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * @author kaize
 */
public class AgenteComprador extends Agent{
    
    private String tituloLibro; // titulo del libro a comprar
    private AID[] agenteVendedor; // arreglo para vendedores
    private int ticker_timer = 10000;  //intervalo de tiempo para buscar vendedores
    private AgenteComprador this_agent = this;
    
    //Configuraciòn del agente
    protected void setup(){
        System.out.println("El agente comprador " + getAID().getName()+ " esta listo");
        
        //Argumentos del agente
        Object[] args = getArguments();
        if(args != getArguments()&& args.length > 0){
            tituloLibro =(String) args[0];
            System.out.println("Intentando comprar el libro: " + tituloLibro);
            
            addBehaviour(new TickerBehaviour(this, ticker_timer){
                protected void onTick(){
                    System.out.println("Intentando comprar el libro: "+ tituloLibro);
                    //Crea descripciòn del agente
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("book-selling");
                    template.addServices(sd);
                    
                    try {
                        //Buscamos agentes que tenga el libro a la venta
                        DFAgentDescription[] resultado = DFService.search(myAgent, template);
                        System.out.println("Se encontraron los siguientes agentes vendedores:");
                        agenteVendedor = new AID[resultado.length];
                        
                          for (int i = 0; i < resultado.length; i++) {
                            agenteVendedor[i] = resultado[i].getName();
                            System.out.println(agenteVendedor[i].getName());
                        }
                        
                    } catch (FIPAException fe){
                        fe.printStackTrace();
                                              
                    }
                    // agregamos comportamiento de servidor solicitud
                    myAgent.addBehaviour(new solicitud(this_agent));
                    
                }
            }
            );          
                     
        } else {
            System.out.println("No se especificò un titlo de libro ");
            doDelete();
        }
    }
     //matando el agente
    protected void takeDown(){
        System.out.println("El agente comprador " + getAID().getName() + "se ah finalizado");
    }
    
    // se optiene lista de vendedores
    public AID[] obtenerAgenteVendedor(){
        return agenteVendedor;
    }
    
    //libros a buscar
    
    public String obtenerTituloLibro(){
        return tituloLibro;
    }
   
    
    
}
