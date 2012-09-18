package com.clouway.chat;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */

public class Server {

  private final List<Socket> clientList = Collections.synchronizedList(new LinkedList<Socket>());

  private List<Display> displayList;

  private ServerSocket serversocket;

  private ClientMessages messages;


  public Server(List<Display> displayList,ClientMessages messages) {

    this.displayList = displayList;

    this.messages = messages;

  }


  public synchronized void startServer(int port) throws IOException, InterruptedException {


    serversocket = new ServerSocket(port);

    new Thread(new Runnable() {

      @Override

      public synchronized void run() {

        try {

          while (!Thread.currentThread().isInterrupted()) {

            Socket clientSocket = serversocket.accept();

            clientSocket.setSoTimeout(250);

            for (Display display : displayList) {

              display.show(messages.onClientConnect(clientList.size()+1));

            }

            writeMessage(clientSocket,messages.displayTotalClients(clientList.size()));

            synchronized (clientList) {

              notifyClients(messages.onClientConnect(clientList.size()+1));

              clientList.add(clientSocket);
            }

          }

        } catch (IOException ignored) { }
      }
    }).start();


    new Thread(new Runnable() {

      @Override
      public synchronized void run() {
        try {
          Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }

        while (!Thread.currentThread().isInterrupted()) {


          synchronized (clientList) {

            for (Socket socket : clientList) {

              try {

                if (socket.getInputStream().read() == -1) {

                  clientList.remove(socket);

                  notifyClients(messages.onClientDisconnect());

                  notifyClients(messages.displayTotalClients(clientList.size()));

                }
              } catch (SocketException ignored) {
              } catch (IOException ignored) {
              }
            }


            try {

              Thread.sleep(250);
            } catch (InterruptedException e) {
              System.out.println("Something , somewhere , somehow went terrible wrong");
            }

          }
        }

      }

    }).start();

  }

  private void writeMessage(Socket socket, String message) throws IOException {

    PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());

    socketWriter.println(message);

    socketWriter.flush();

  }


  private synchronized void notifyClients(String message) throws IOException {

    for (Socket socket : clientList) {

      writeMessage(socket, message);

    }

  }

  public void stopServer() throws IOException {

    serversocket.close();

  }


}
