package com.clouway.singleconnection;

/**
 * @author georgi.hristov@clouway.com
 */
public interface Dispatcher {

  void dispatchMessage(String message,Application user);

}
