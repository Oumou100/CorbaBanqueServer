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
            // Création d'un contexte JNDI pour accéder au service de nommage CORBA.
            Context ctx = new InitialContext();

            // Recherche de l'objet distant enregistré sous le nom "BK" dans l'annuaire.
            // La méthode 'lookup' retourne une référence générique de type java.lang.Object.
            java.lang.Object ref = ctx.lookup("BK");

            // Conversion (cast) de la référence générique en une référence CORBA spécifique.
            // Le cast utilise la méthode 'narrow' fournie par le stub helper généré par l'IDL.
            IBanqueRemote stub = IBanqueRemoteHelper.narrow((Object) ref);

            // Invocation d'une méthode distante : conversion d'un montant (par exemple, 600 unités).
            // Cette méthode est appelée sur le stub, qui agit comme un proxy vers l'objet distant.
            System.out.println(stub.conversion(600));

            // Invocation d'une méthode distante : récupération des informations d'un compte spécifique.
            // Le code du compte (4 dans cet exemple) est passé en paramètre.
            Compte c = stub.getCompte(4);

            // Affichage des détails du compte obtenu (par exemple, le solde).
            System.out.println("Solde = " + c.solde);

        } catch (NamingException e) {
            // Gestion des exceptions liées à JNDI (problèmes lors de la recherche de l'objet distant).
            e.printStackTrace();
        }
    }
}
