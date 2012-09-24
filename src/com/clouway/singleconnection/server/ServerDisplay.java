package com.clouway.singleconnection.server;

import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class ServerDisplay implements Display {

  private ServerMessages messages = new ServerMessages();

  private JFrame serverWindow;

  private JTextArea textArea = new JTextArea();

  private JButton startButton = new JButton("Start Server");

  private JButton stopButton = new JButton("Stop Server");

  private final Application server;

  public ServerDisplay(Application server) {

    this.server = server;

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
          server.start();
          show(messages.serverStarted());
        } catch (IOException e1) {
          show(messages.errorAlreadyStarted());
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
          server.stop();
          if (!stopped) {
            show(messages.serverStopped());
            stopped = true;
          } else {
            show(messages.errorAlreadyStopped());
          }
        } catch (IOException e1) {
          show(messages.errorAlreadyStopped());
        } catch (NullPointerException ne) {
          show(messages.errorNullPointer());
        }
      }
    });

  }

  @Override
  public void show(String message) {

    textArea.append(message + '\n');

  }
}
