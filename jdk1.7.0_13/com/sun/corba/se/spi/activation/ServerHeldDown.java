package com.sun.corba.se.spi.activation;


/**
* com/sun/corba/se/spi/activation/ServerHeldDown.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../../../../src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Wednesday, January 30, 2013 10:05:12 AM PST
*/

public final class ServerHeldDown extends org.omg.CORBA.UserException
{
  public int serverId = (int)0;

  public ServerHeldDown ()
  {
    super(ServerHeldDownHelper.id());
  } // ctor

  public ServerHeldDown (int _serverId)
  {
    super(ServerHeldDownHelper.id());
    serverId = _serverId;
  } // ctor


  public ServerHeldDown (String $reason, int _serverId)
  {
    super(ServerHeldDownHelper.id() + "  " + $reason);
    serverId = _serverId;
  } // ctor

} // class ServerHeldDown
