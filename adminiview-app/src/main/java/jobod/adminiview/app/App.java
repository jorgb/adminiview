package jobod.adminiview.app;

import jobod.adminiview.generator.AdminiGenerator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Hello world!
 *
 */
public class App 
{	
    public static void main( String[] args )
    {    	
		AdminiGenerator generator = new AdminiGenerator();

		Options options = generator.getOptions();		    	
		options.addOption("h", false, "Prints this help");
		
		CommandLineParser parser = new PosixParser();
    	try {
    		CommandLine cmd = parser.parse(options, args);
			if(cmd.hasOption('h')) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "adminiview", options );
				System.exit(1);
			}
			
			generator.intialize(cmd);
						
		} catch (ParseException e) {
			System.err.println("Command line error: " + e.getMessage());
			System.exit(1);
		}
    	
		int errCode = generator.execute();
		if(errCode == 0) {
			System.out.println("Generation completed!");
		}
		else {
			System.err.println("Generation aborted with error : " + errCode);
		}
    }
}
