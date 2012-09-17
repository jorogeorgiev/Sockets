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

// Abandon all hope, ye who enter here.
public class Server {

  private volatile List<Socket> clientList = Lists.newLinkedList();

  private List<Display> displayList;

  private ServerSocket serversocket;


  public Server(List<Display> displayList) {

    this.displayList = displayList;

  }


  public synchronized void startServer(int port) throws IOException, InterruptedException {

    serversocket = new ServerSocket(port);

    new Thread(new Runnable() {

      @Override

      public void run() {

        try {

          while (!Thread.currentThread().isInterrupted()) {

           Socket clientSocket = serversocket.accept();

            clientSocket.setSoTimeout(1000);

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            for (Display display : displayList) {

              display.show("Connected");

            }

            writer.println("There are " + clientList.size() + " connected users.");

            writer.flush();


            for (Socket socket : clientList) {

              PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());

              socketWriter.println("Client #" + (clientList.size() + 1) + " has just connected");

              socketWriter.flush();

            }

            clientList.add(clientSocket);

          }

        } catch (IOException ignored) {
        }


      }
    }).start();

    Thread.sleep(100);

  }


  public void stopServer() throws IOException {

    serversocket.close();

  }


}
