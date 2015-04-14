package com.jujutsu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {
	public static double[][] simpleRead2DMatrix(File file) {
		return simpleRead2DMatrix(file, " ", 10000);
	}

	
	public static double[][] simpleRead2DMatrix(File file, String columnDelimiter) {
		return simpleRead2DMatrix(file, columnDelimiter, 5000);

	}
		
	public static double[][] simpleRead2DMatrix(File file, String columnDelimiter, int linesLimit) {
		List<double[]> rows = new ArrayList<>();
		System.out.println("columnDelimiter="+columnDelimiter);
		int aux = 0;
		String[] cols = null;
		double [] row ; //changed CLARISSA
		
        try (FileReader fr = new FileReader(file)) {
            BufferedReader b = new BufferedReader(fr);
            String line;
            while ((line = b.readLine()) != null && !line.matches("\\s*") && (aux < linesLimit)) { //Works only for the first 5000 lines
            	aux = aux+1;
                //cols = line.trim().split(columnDelimiter);
                //CLARISSA
                if (cols == null) {
                    if (linesLimit > line.trim().split(columnDelimiter).length)
                    	linesLimit = line.trim().split(columnDelimiter).length;
            		System.out.println("linesLimit="+linesLimit);
                	cols = new String[linesLimit] ; //changed CLARISSA
                }
             	System.arraycopy(line.trim().split(columnDelimiter), 0, cols, 0, linesLimit);
                row = new double[cols.length];
                for (int j = 0; (j < cols.length) && (j< linesLimit); j++) {
         //   		System.out.println("cols.length="+cols.length+ " j = "+j+" line = "+ aux);
                	if(!(cols[j].length()==0)) {
                		row[j] = Double.parseDouble(cols[j].trim());
                    }
                }
                rows.add(row);
            }
            b.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        
        double[][] array = new double[rows.size()][];
        int currentRow = 0;
        for (double[] ds : rows) {
			array[currentRow++] = ds;
		}
                
        return array;
    }
	
	public static String[] simpleReadLines(File file) {
		List<String> rows = new ArrayList<>();
		
        try (FileReader fr = new FileReader(file);
        	BufferedReader b = new BufferedReader(fr)) {
        	String line;
        	while ((line = b.readLine()) != null) {
        		rows.add(line.trim());
        	}
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        
        String[] lines = new String[rows.size()];
        int currentRow = 0;
        for (String line : rows) {
        	lines[currentRow++] = line;
		}
                
        return lines;
    }
}
