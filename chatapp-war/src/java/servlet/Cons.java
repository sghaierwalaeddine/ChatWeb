/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import EntityBean.Conversation;
import EntityBean.Message;
import EntityBean.Utilisateur;
import EntityBeans.MessageFacadeLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.ChatMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 *
 * @author Rached
 */
@WebServlet(name = "Cons", urlPatterns = {"/Cons"})
public class Cons extends HttpServlet {

    @EJB
    private MessageFacadeLocal messageFacade;

       public Cons() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String topic = "test";
        Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("group.id", "test");
props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);

// Subscribe to the topic that the producer is sending messages to
consumer.subscribe(Collections.singletonList(topic));
         
        
         System.out.println("Souscris au topic " + topic);
     
         int i = 0;

    while (true) {
       ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(1000));
for (ConsumerRecord<String, byte[]> record : records) {
  // Deserialize the object from the message
  ObjectMapper mapper = new ObjectMapper();
  ChatMessage chatMessage = mapper.readValue(record.value(), ChatMessage.class);
  // Use the deserialized object as needed in your application
    Utilisateur u = new Utilisateur();
                    Conversation c = new Conversation();
                    Message m = new Message();
                    u.setUserid(Integer.parseInt(chatMessage.getSender()));
                    c.setConversationid(Integer.parseInt(chatMessage.getConvId()));
                    m.setConvid(c);
                    m.setSender(u);
                    m.setText(chatMessage.getMessage());
                    m.setSentat(new Date());
                    if (chatMessage.getMessage() != "") {
                        messageFacade.create(m);

                    }  
}
        }
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    }
