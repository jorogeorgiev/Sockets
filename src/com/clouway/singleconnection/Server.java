package com.clouway.singleconnection;

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

  private int portNumber;

  private List<Display> displayList;

  private List<String> serverMessage = Lists.newArrayList();

  private ServerSocket serverSocket;

  private Socket clientSocket;

  public Server(int portNumber, List<Display> displayList, String... serverMessages) {

    this.portNumber = portNumber;

    this.displayList = displayList;

    for (String string : serverMessages) {

      serverMessage.add(string);

    }

  }

  public void startServer() throws IOException {

    serverSocket = new ServerSocket(portNumber);


    new Thread(new Runnable() {
      @Override
      public void run() {

        try {

          clientSocket = serverSocket.accept();

          for (Display display : displayList) {

            display.show(serverMessage.get(1));

          }

          PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

          writer.println(serverMessage.get(0));

          writer.flush();


        } catch (IOException io) {


          System.out.println("There were no clients on the server!");

        }

      }
    }).start();

  }


  public void stopServer() throws IOException {

    if(clientSocket!=null){

      System.out.println("A mother fucker was disconnected");

    }

    serverSocket.close();

  }

}
