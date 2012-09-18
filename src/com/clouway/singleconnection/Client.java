package com.clouway.singleconnection;


import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Client {

  private Socket clientSocket;

  private List<Display> displayList;

  private Scanner scanner;

//  private StringBuilder receivedMessage = new StringBuilder();

  public Client(List<Display> displayList) {

    this.displayList = displayList;

  }


  public void connectTo(String hostAddress, int serverPort) throws InterruptedException, IOException {

    clientSocket = new Socket(hostAddress, serverPort);

    new Thread(new Runnable() {

      @Override
      public void run() {

        try {

          scanner = new Scanner(clientSocket.getInputStream());

            while (scanner.hasNextLine()) {

             /* for (Display display : displayList) {

                display.show(scanner.nextLine());

              }*/
              System.out.println(scanner.nextLine());

            }



          for (Display display : displayList) {

            display.show("Connection Lost");



          }

          clientSocket.close();

        } catch (IOException e) {

          System.out.println("Something Went Terribly Wrong");

        }

      }

    }).start();

  }

}
