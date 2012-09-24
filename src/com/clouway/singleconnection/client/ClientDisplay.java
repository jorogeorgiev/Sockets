package com.clouway.singleconnection.client;

import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Display;
import com.clouway.singleconnection.server.ServerMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class ClientDisplay implements Display {

  private ServerMessages messages = new ServerMessages();

  private JFrame serverWindow;

  private JTextArea textArea = new JTextArea();

  private JButton startButton = new JButton("Connect Client");

  private JButton stopButton = new JButton("Disconnect Client");

  private final Application client;

  public ClientDisplay(Application client) {

    this.client = client;
    buildDisplay();

  }

  private void buildDisplay() {

    serverWindow = new JFrame();

    serverWindow.setSize(440, 200);

    serverWindow.setLocation(150, 150);

    serverWindow.setLayout(new FlowLayout());

    serverWindow.setVisible(true);

    serverWindow.add(startButton);

    startButton.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          client.start();
          show("Client Connected");
        } catch (IOException e1) {
          show("Server is out of order");
        }
      }
    });

    serverWindow.add(textArea);

    serverWindow.add(stopButton);

    stopButton.addActionListener(new AbstractAction() {
      public boolean stopped = false;

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          client.stop();
          if (!stopped) {
            show("Client Disconnected");
            stopped = true;
          } else {
            show("Client Already Disconnected");
          }
        } catch (IOException e1) {
          show("Go fish");
        } catch (NullPointerException ne) {
          show("Double time");
        }
      }
    });

  }

  @Override
  public void show(String message) {

    textArea.append(message + '\n');

  }
}
