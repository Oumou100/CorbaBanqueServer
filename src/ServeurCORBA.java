import javax.naming.Context;
import javax.naming.InitialContext;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import service.BanqueImpl;

public class ServeurCORBA {

    public static void main(String[] args) {
        try {
            // Initialisation de l'ORB (Object Request Broker), le middleware CORBA.
            // 'args' contient des param�tres comme l'adresse IP ou le port du service de nommage.
            ORB orb = ORB.init(args, null);

            // Cr�ation d'un contexte JNDI pour interagir avec le service de nommage CORBA.
            Context ctx = new InitialContext();

            // R�solution de la r�f�rence du POA racine (RootPOA).
            // POA (Portable Object Adapter) est responsable de la gestion des objets CORBA c�t� serveur.
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // Conversion explicite via narrow.
            
            // Activation du gestionnaire du POA pour permettre la r�ception des requ�tes.
            poa.the_POAManager().activate();

            // Cr�ation d'une instance de l'impl�mentation de l'objet distant.
            BanqueImpl od = new BanqueImpl();

            // Publication de la r�f�rence de l'objet distant dans l'annuaire via le contexte JNDI.
            // 'BK' est le nom symbolique sous lequel l'objet est enregistr�.
            ctx.rebind("BK", poa.servant_to_reference(od)); 
            // Remarque : En CORBA, on utilise 'servant_to_reference' pour obtenir la r�f�rence � l'objet distant.
            // Contrairement � RMI, o� 'toString()' peut directement retourner cette r�f�rence.

            // Lancement du serveur ORB pour �couter et traiter les requ�tes des clients.
            orb.run();

            // Note : Le driver JNDI utilis� ici est 'com.sun.jndi.cosnaming.CNCtxFactory',
            // qui est sp�cifiquement con�u pour interagir avec le service de nommage CORBA.

        } catch (Exception e) {
            // Gestion des exceptions pour capturer et afficher toute erreur �ventuelle.
            e.printStackTrace();
        }
    }
}
