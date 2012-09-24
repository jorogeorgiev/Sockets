package com.clouway.singleconnection;

import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public interface Application {

  void start() throws IOException;

  void stop() throws IOException;

}
