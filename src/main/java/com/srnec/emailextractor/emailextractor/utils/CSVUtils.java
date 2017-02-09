package com.srnec.emailextractor.emailextractor.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Utility class for handling CSV files.
 * 
 * @author vape
 */
public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ';';

    /**
     * Writes a CSV file ({@code outputFile}) with given data ({@code lines}). It uses ';' as default separator.
     * 
     * @param outputFile
     * @param lines
     * @throws IOException
     */
    public static void write(File outputFile, List<String[]> lines) throws IOException {
    	Objects.requireNonNull(outputFile);
    	Objects.requireNonNull(lines);
    	
    	outputFile.createNewFile();
    	
    	try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile), DEFAULT_SEPARATOR)) {
    		writer.writeAll(lines);
    	}
    }

}
