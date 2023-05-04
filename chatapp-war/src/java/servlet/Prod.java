/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Rached
 */
@WebServlet(name = "Prod", urlPatterns = {"/Prod"})
public class Prod extends HttpServlet {

    public Prod() {
        super();}
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
        String msg = request.getParameter("msg");


     	// String topicName =topic;
         // create instance for properties to access producer configs   
         Properties props = new Properties();
         
         //Assign localhost id
         props.put("bootstrap.servers", "localhost:9092");
         
         //Set acknowledgements for producer requests.      
         props.put("acks", "all");
         
         //If the request fails, the producer can automatically retry,
         props.put("retries", 0);
         
         //Specify buffer size in config
         props.put("batch.size", 16384);
         
         //Reduce the no of requests less than 0   
         props.put("linger.ms", 1);
         
         //The buffer.memory controls the total amount of memory available to the producer for buffering.   
         props.put("buffer.memory", 33554432);
         
         props.put("key.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");
            
         props.put("value.serializer", 
            "org.apache.kafka.common.serialization.StringSerializer");
         
         Producer<String, String> producer = new KafkaProducer
            <String, String>(props); 
         
         //send the message to a topic 
         producer.send(new ProducerRecord<String, String>(topic,msg));
         producer.close();
	         
	         out.println("<html>");
	         out.println("<head><title>Write to topic: "+ topic +"</title></head>");
	         out.println("<body> message <b> "+ msg +" <b>has been sent to topic:<b> "+ topic +" </h1> ");
	         out.println("</html>");
	         out.close();
         
           producer.close();
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
