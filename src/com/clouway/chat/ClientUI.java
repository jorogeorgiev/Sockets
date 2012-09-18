package com.clouway.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientUI implements Display {

  private JTextArea area;
  private Client client;
  private boolean isConnected = false;

  public ClientUI() {

    JButton connectButton = new JButton("connect to server");

    connectButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {


        new Thread(new Runnable() {


          @Override
          public void run() {
            try {

              while (!isConnected) {

                client.connectTo();

                isConnected = true;

              }
            } catch (IOException e1) {

              area.setText("GO FISH!!!!!!!");

            }
          }
        }).start();


      }

    });

    area = new JTextArea();
    area.setRows(20);
    area.setColumns(20);

    JFrame serverFrame = new JFrame("server frame");
    serverFrame.setLayout(new FlowLayout());
    serverFrame.setSize(500, 500);
    serverFrame.setLocation(500, 500);
    serverFrame.setVisible(true);
    serverFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    serverFrame.add(area);
    serverFrame.add(connectButton);


  }

  public void addClient(Client client) {
    this.client = client;
  }


  @Override
  public void show(String message) {

    area.setText(message + " ");



  }
}
