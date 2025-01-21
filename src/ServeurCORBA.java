import javax.naming.Context;
import javax.naming.InitialContext;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import service.BanqueImpl;

public class ServeurCORBA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ORB orb = ORB.init(args, null);//Middleware Corba args ex adresse IP, port de l'annuaire
			Context ctx = new InitialContext();
			
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA")); //Casting avec narrow
			poa.the_POAManager().activate();
			BanqueImpl od = new BanqueImpl();
			ctx.rebind("BK", poa.servant_to_reference(od)); //c'est la reference de l'objet qu'on publie dans l'annuaire
			//Par contre avec RMI on a la methode toString qui va retourner la reference automatiquement c'est elle qui est utilisée
			orb.run();
			
			//com.sun.jndi.cosnaming.CNCtxFactory pilote JNDI pour l'annuaire Corba
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
