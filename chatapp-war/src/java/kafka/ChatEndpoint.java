package kafka;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import EntityBean.Conversation;
import EntityBean.Message;
import EntityBean.Utilisateur;
import EntityBeans.MessageFacadeLocal;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Stateful
@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint extends HttpServlet {

    @EJB
    private MessageFacadeLocal messageFacade;

    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        System.out.println("****************session openend and bound to room******************: " + room);
        session.getUserProperties().put("room", room);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()
                        && room.equals(s.getUserProperties().get("room"))) {
                   s.getBasicRemote().sendObject(chatMessage) ;
               
                 

                }
            }
             
               
                     String topic = "test";
        


     	// String topicName =topic;
         // create instance for properties to access producer configs   
         Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);

// Serialize the object that you want to send as a message
ObjectMapper mapper = new ObjectMapper();
byte[] serializedObject = mapper.writeValueAsBytes(chatMessage);

// Send the serialized object as a message to the Kafka topic
ProducerRecord<String, byte[]> record = new ProducerRecord<>("test", serializedObject);
producer.send(record);
         producer.close();
            
                    
        } catch (Exception e) {
            System.out.print("dhshd");
        }
    }
}
