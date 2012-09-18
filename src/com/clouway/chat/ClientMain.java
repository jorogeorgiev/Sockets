package com.clouway.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientMain {

  public static void main(String[] args) {

    ClientUI clientUI = new ClientUI();

    Client client = new Client("localhost", 1930, clientUI);

    clientUI.addClient(client);


  }
}
