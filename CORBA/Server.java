import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

class HelloImpl extends HelloPOA {
    public String sayHello() {
        return "Hello, World from CORBA!";
    }
}

public class Server {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            HelloImpl helloImpl = new HelloImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
            Hello helloRef = HelloHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent path[] = ncRef.to_name("Hello");
            ncRef.rebind(path, helloRef);
            System.out.println("Server is running...");
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
