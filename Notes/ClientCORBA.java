import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.omg.CORBA.Object;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

public class ClientCORBA {

    public static void main(String[] args) {
        try {
            // Cr�ation d'un contexte JNDI pour acc�der au service de nommage CORBA.
            Context ctx = new InitialContext();

            // Recherche de l'objet distant enregistr� sous le nom "BK" dans l'annuaire.
            // La m�thode 'lookup' retourne une r�f�rence g�n�rique de type java.lang.Object.
            java.lang.Object ref = ctx.lookup("BK");

            // Conversion (cast) de la r�f�rence g�n�rique en une r�f�rence CORBA sp�cifique.
            // Le cast utilise la m�thode 'narrow' fournie par le stub helper g�n�r� par l'IDL.
            IBanqueRemote stub = IBanqueRemoteHelper.narrow((Object) ref);

            // Invocation d'une m�thode distante : conversion d'un montant (par exemple, 600 unit�s).
            // Cette m�thode est appel�e sur le stub, qui agit comme un proxy vers l'objet distant.
            System.out.println(stub.conversion(600));

            // Invocation d'une m�thode distante : r�cup�ration des informations d'un compte sp�cifique.
            // Le code du compte (4 dans cet exemple) est pass� en param�tre.
            Compte c = stub.getCompte(4);

            // Affichage des d�tails du compte obtenu (par exemple, le solde).
            System.out.println("Solde = " + c.solde);

        } catch (NamingException e) {
            // Gestion des exceptions li�es � JNDI (probl�mes lors de la recherche de l'objet distant).
            e.printStackTrace();
        }
    }
}
