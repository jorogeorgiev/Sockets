package com.clouway.singleconnection;
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

  private String serverMessage;

  private ServerSocket serverSocket;

  private Socket clientSocket;

  public Server(int portNumber, String serverMessage, List<Display> displayList) {

    this.portNumber = portNumber;

    this.serverMessage = serverMessage;

    this.displayList = displayList;

  }

  public void startServer() throws IOException {

    serverSocket = new ServerSocket(portNumber);


    new Thread(new Runnable() {
      @Override
      public void run() {

        try {

          clientSocket = serverSocket.accept();

          for (Display display : displayList) {

            display.show("Client Connected");

          }

          PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

          writer.println(serverMessage);

          writer.flush();

        } catch (IOException io) {

          System.out.println("Dead Socket");

        }

      }
    }).start();

  }


  public void stopServer() throws IOException {

    serverSocket.close();

    for (Display display : displayList) {

      display.closeDisplay();

    }

  }

}
