package com.clouway.singleconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerUI implements Display {

  private JFrame serverFrame = new JFrame("server frame");
  private ArrayList<Server> servers;
  private JTextArea area;
  private StringBuilder serverMessage = new StringBuilder();
  private JButton closeButton;

  public ServerUI() {

    closeButton = new JButton("close server");

    closeButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        closeDisplay();
      }

    });

    area = new JTextArea();
    area.setRows(20);
    area.setColumns(20);

    serverFrame.setLayout(new FlowLayout());
    serverFrame.setSize(500, 500);
    serverFrame.setLocation(500, 500);
    serverFrame.setVisible(true);
    serverFrame.add(area);
    serverFrame.add(closeButton);


  }

  public void listenServer(ArrayList<Server> serverList) {

    this.servers=serverList;

  }


  @Override
  public void show(String message) {
    serverMessage.append(message);
    area.setText(serverMessage.toString());
  }

  @Override
  public void closeDisplay() {
    try {

     for(Server server : servers){


       server.stopServer();


     }

      serverFrame.dispose();

    } catch (IOException e) {
    }


  }
}
