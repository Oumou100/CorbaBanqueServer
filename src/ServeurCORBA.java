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
            // 'args' contient des paramètres comme l'adresse IP ou le port du service de nommage.
            ORB orb = ORB.init(args, null);

            // Création d'un contexte JNDI pour interagir avec le service de nommage CORBA.
            Context ctx = new InitialContext();

            // Résolution de la référence du POA racine (RootPOA).
            // POA (Portable Object Adapter) est responsable de la gestion des objets CORBA côté serveur.
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // Conversion explicite via narrow.
            
            // Activation du gestionnaire du POA pour permettre la réception des requêtes.
            poa.the_POAManager().activate();

            // Création d'une instance de l'implémentation de l'objet distant.
            BanqueImpl od = new BanqueImpl();

            // Publication de la référence de l'objet distant dans l'annuaire via le contexte JNDI.
            // 'BK' est le nom symbolique sous lequel l'objet est enregistré.
            ctx.rebind("BK", poa.servant_to_reference(od)); 
            // Remarque : En CORBA, on utilise 'servant_to_reference' pour obtenir la référence à l'objet distant.
            // Contrairement à RMI, où 'toString()' peut directement retourner cette référence.

            // Lancement du serveur ORB pour écouter et traiter les requêtes des clients.
            orb.run();

            // Note : Le driver JNDI utilisé ici est 'com.sun.jndi.cosnaming.CNCtxFactory',
            // qui est spécifiquement conçu pour interagir avec le service de nommage CORBA.

        } catch (Exception e) {
            // Gestion des exceptions pour capturer et afficher toute erreur éventuelle.
            e.printStackTrace();
        }
    }
}
