package com.clouway.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Client {


  private String hostAddress;
  private int port;
  private Display clientUI;
  private StringBuilder builder = new StringBuilder();

  public Client (String hostAddress , int port, Display clientUI){


    this.hostAddress = hostAddress;

    this.port = port;

    this.clientUI = clientUI;

  }


   public void connectTo() throws IOException {

     final Socket socket = new Socket(hostAddress,port);

     new Thread(new Runnable() {

       @Override

       public void run() {

         try {

           Scanner scan = new Scanner(socket.getInputStream());

           while (scan.hasNextLine()) {

              builder.append(scan.nextLine()).append("\n");

              clientUI.show(builder.toString());

           }

           clientUI.show("Connection Lost");


         } catch (IOException ignored) {}

       }
     }).start();



   }








}
