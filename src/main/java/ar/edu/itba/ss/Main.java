package ar.edu.itba.ss;

import ar.edu.itba.ss.Observers.EnergyObserver;
import ar.edu.itba.ss.Observers.FlowObserver;
import ar.edu.itba.ss.Observers.OVITOObserver;
import ar.edu.itba.ss.Observers.SpaceObserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Double diameter, width, height, dt, tf, dtObserver;
    private static int N;

    public static void main( String[] args ) throws Exception {
        // Parse arguments
        parse("params.txt");
        Logger.log("Arguments parsed!");

        // Create observers
        SpaceObserver observerOVITO = new OVITOObserver("ovito_out/ovito.xyz", tf, 25.0);
        SpaceObserver observerEnergy = new EnergyObserver("energy_out/energy.csv", tf, dtObserver);
        SpaceObserver observerFlow = new FlowObserver("flow_out/flow.csv", tf, dtObserver);
        Logger.log("Observer created!");

        // Create space
        Space space = new Space(width, height, diameter, N);
        Logger.log("Space created!");

        // Attach observer to space
        space.attachObserver(observerOVITO);
        space.attachObserver(observerEnergy);
        space.attachObserver(observerFlow);
        Logger.log("Observers attached to space!");

        // Create simulator (pass ovito observer to end the simulation on time)
        Simulator simulator = new Simulator(space, observerOVITO, dt);
        Logger.log("Simulator created!");

        // Add the rest of the observers
        simulator.attachObserver(observerEnergy);
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
                case "dt_observer": dtObserver = Double.parseDouble(aux[1]); break;
                case "tf" : tf = Double.parseDouble(aux[1]); break;
                default:
                    throw new RuntimeException("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<6){
            throw new RuntimeException("THERE ARE PARAMETERS MISSING");
        }
    }


}
