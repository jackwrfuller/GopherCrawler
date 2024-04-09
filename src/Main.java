public class Main {

    static String serviceHost = "gopher.quux.org";
    static int servicePort = 70;

    /** Handle command line arguments. */
    protected static void processArgs(String[] args)
    {
        //  This program has only two CLI arguments, and we know the order.
        //  For any program with more than two args, use a loop or package.
        if (args.length > 0) {
            serviceHost = args[0];
            if (args.length > 1) {
                servicePort = Integer.parseInt(args[1]);
            }
        }
    }

    public static void main(String[] args)
    {
        try {
            processArgs(args);
            var client = new GopherClient(serviceHost, servicePort);
            String response = client.send("/Archives");

            System.out.println("Done.");
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
