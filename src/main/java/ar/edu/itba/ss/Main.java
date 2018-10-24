package ar.edu.itba.ss;

import ar.edu.itba.ss.Observers.FlowObserver;
import ar.edu.itba.ss.Observers.OVITOObserver;
import ar.edu.itba.ss.Observers.SpaceObserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Double diameter, width, height, dt;
    private static int N;

    public static void main( String[] args ) throws Exception {
        // Parse arguments
        parse("params.txt");
        Logger.log("Arguments parsed!");

        // Create observers
        SpaceObserver observerOVITO = new OVITOObserver("ovito_out/ovitoV5.5.xyz", N, 25.0);
        SpaceObserver observerFlow = new FlowObserver("flow_out/flowV5.5.csv",  N, dt);
        Logger.log("Observer created!");

        // Create space
        Space space = new Space(width, height, diameter, N);
        Logger.log("Space created!");

        // Attach observer to space
        space.attachObserver(observerOVITO);
        space.attachObserver(observerFlow);
        Logger.log("Observers attached to space!");

        // Create simulator (pass ovito observer to end the simulation on time)
        Simulator simulator = new Simulator(space, observerOVITO, dt);
        Logger.log("Simulator created!");

        // Add the rest of the observers
        simulator.attachObserver(observerFlow);

        // Simulate
        Logger.log("Simulating...");
        simulator.simulate();

    }

    public static void parse(String argsFile) throws IOException {
        FileReader input = new FileReader(argsFile);
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        int i = 0;

        dt = 0.0001;

        while ( (myLine = bufRead.readLine()) != null)
        {
            String[] aux = myLine.toLowerCase().split(" = ");
            switch (aux[0]){
                case "diameter" : diameter = Double.parseDouble(aux[1]); break;
                case "width" : width = Double.parseDouble(aux[1]); break;
                case "height" : height = Double.parseDouble(aux[1]); break;
                case "n" : N = Integer.parseInt(aux[1]); break;
                default:
                    throw new RuntimeException("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<4){
            throw new RuntimeException("THERE ARE PARAMETERS MISSING");
        }
    }


}
