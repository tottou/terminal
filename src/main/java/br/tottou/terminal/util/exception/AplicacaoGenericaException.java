package br.tottou.terminal.util.exception;

import org.apache.commons.lang.exception.NestableException;

public class AplicacaoGenericaException extends NestableException
{
  private static final long serialVersionUID = 4017338091556008801L;

  public AplicacaoGenericaException(String pMsg)
  {
    super(pMsg);
  }

  public AplicacaoGenericaException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  public AplicacaoGenericaException(Throwable cause)
  {
    super(cause);
  }
}