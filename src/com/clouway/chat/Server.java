package com.clouway.chat;


import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Server {

  private List<Socket> clientList = Lists.newLinkedList();

  private List<Display> displayList;

  private ServerSocket serversocket;

  public Server(List<Display> displayList) {

    this.displayList = displayList;

  }

  public void startServer(int port) throws IOException {

    serversocket = new ServerSocket(port);

    new Thread(new Runnable() {

      @Override

      public void run() {


        Socket clientSocket;

        try {

          while (!Thread.currentThread().isInterrupted()) {

            clientSocket = serversocket.accept();

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            writer.println("There are " + clientList.size() +  " connected users.");

            writer.flush();

            clientList.add(clientSocket);



            for (Display display : displayList) {

              display.show("Connected");

            }



          }

        } catch (IOException ignored) { }


      }
    }).start();


  }


  public void stopServer() throws IOException {

    serversocket.close();

  }


}
