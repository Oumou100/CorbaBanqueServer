package corbaBanque;


/**
* corbaBanque/Compte.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Banque.idl
* mardi 21 janvier 2025 04 h 08 CET
*/

public final class Compte implements org.omg.CORBA.portable.IDLEntity
{
  public int code = (int)0;
  public float solde = (float)0;

  public Compte ()
  {
  } // ctor

  public Compte (int _code, float _solde)
  {
    code = _code;
    solde = _solde;
  } // ctor

} // class Compte
