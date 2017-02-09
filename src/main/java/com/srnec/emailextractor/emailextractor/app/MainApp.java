package com.srnec.emailextractor.emailextractor.app;

import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.srnec.emailextractor.emailextractor.engine.ExtractorEngine;

public class MainApp {

	public static void main(String[] args) {
		CommandLine cmd = initArgs(args);
		String inputFilePath = cmd.getOptionValue("input", System.getProperty("user.dir"));
        String outputFilePath = cmd.getOptionValue("output", System.getProperty("user.dir"));
        boolean isDebug = cmd.hasOption("debug");
        int depth;
        int time;
        try {
			depth = (int) Optional.ofNullable(cmd.getParsedOptionValue("depth")).orElse(3);
			time = (int) Optional.ofNullable(cmd.getParsedOptionValue("time")).orElse(1);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
        
		ExtractorEngine emailParserService = new ExtractorEngine(inputFilePath, outputFilePath, depth, isDebug, time);
		emailParserService.extractEmails();
	}
	
	private static CommandLine initArgs(String[] args) {
		Options options = new Options();
        Option input = new Option("i", "input", true, "input folder [default - current directory]");
        input.setRequired(false);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output folder [default - current directory]");
        output.setRequired(false);
        options.addOption(output);

        Option maxDepth = new Option("l", "level", true, "level of directories to visit (depth) [default - 3]");
        maxDepth.setType(int.class);
        options.addOption(maxDepth);
        
        Option time = new Option("t", "time", true, "specifies max. time in hours for how long it will keep running the script [default - 1]");
        time.setType(int.class);
        options.addOption(time);
        
        Option debug = new Option("d", "debug", false, "enables debug output");
        options.addOption(debug);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("emailextractor", options);
            System.exit(1);
        }
		
		return cmd;
	}

}
