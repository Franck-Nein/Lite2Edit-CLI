package goldendelicios.lite2editcli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lite2EditCli {
	public static void main(String[] args) {
		//Parse the arguments
		String outputDir = null;
		List<String> inputFiles = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-i")) {
				inputFiles.add(args[i + 1]);
			}
			if (args[i].equals("-o")) {
				outputDir = args[i + 1];
			}
		}

		// Validate arguments
		if (outputDir == null || inputFiles.isEmpty()) {
			System.out.println("Usage: Lite2Edit -i input1 -i input2 ... -i inputN -o output");
			System.exit(1);
		}

		try {
			Files.deleteIfExists(Paths.get("errors.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String inputFile : inputFiles) {
			File input = new File(inputFile);
			File output = new File(outputDir);
			if(input.isDirectory()){
				File[] filesInDirectory = input.listFiles();
				for(File f : filesInDirectory){
					convertFile(f, output);
				}
			}else{
				convertFile(input, output);
			}
		}
	}
	private static void convertFile(File input, File output) {
		try {
			Converter.litematicToWorldEdit(input, output);
			System.out.println("Converted " + input.getName() + " to WorldEdit format and saved to " + output);
		} catch (IOException e) {
			System.out.println("An error occurred while converting " + input.getName() + ": " + e.getMessage());
		}
	}
}