package CompressionDecompressionPackage;

import FilesPackage.FileToCompress;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.io.IOException;


public class CompressionModule {

    private int compressionLevel = 2;
    private ProgressMonitor progressMonitor;
    private ZipFile zipFile;
    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    public void compressToZip(FileToCompress fileToProcess, String filePassword) {
        // Try block to check if any exception occurs
        try {

            // Creating encryption zipParameters
            // for password protection
            ZipParameters zipParameters = new ZipParameters();

            // Setting encryption of files
            zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);

            // setting compression level of files
            if(compressionLevel == 0){
                zipParameters.setCompressionLevel(CompressionLevel.FASTEST);
            } else if (compressionLevel == 1) {
                zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
            } else if (compressionLevel == 2) {
                zipParameters.setCompressionLevel(CompressionLevel.ULTRA);
            }


            // Setting encryption of files to true
            zipParameters.setEncryptFiles(true);

            // Setting encryption method
            zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);


            // Creating ZIP file
            new File(fileToProcess.getDestinationPath());
            zipFile = new ZipFile(fileToProcess.getDestinationPath());
            progressMonitor = zipFile.getProgressMonitor();
            // Creating list of files to be added to ZIP file
            //ArrayList<File> list = new ArrayList<>();
            ////Add SPECIFIC files
            //list.add(new File("D:\\Tenet.2020.720p.HDRip.900MB.x264-GalaxyRG[TGx]\\Tenet.2020.720p.HDRip.900MB.x264-GalaxyRG.mkv"));


            if (filePassword != null) {
                zipFile.setPassword(filePassword.toCharArray());//creating zip file password
            }

            //zipFile.addFiles(list, zipParameters);
            zipFile.setRunInThread(true);//making progress monitor run in background
            if (fileToProcess.getFile().isFile()) {
                zipFile.addFile(fileToProcess.getFile(), zipParameters);
            } else if (fileToProcess.getFile().isDirectory()) {
                zipFile.addFolder(fileToProcess.getFile(), zipParameters);
            }



            zipFile.setRunInThread(false);//ending zipfile's thread

            // Print the destination in the local directory
            // where ZIP file is created
            System.out.println("Password protected Zip file" + "have been created at " + fileToProcess.getDestinationPath().toString());

            // Catch block to handle the exceptions
        } catch (IOException e) {

            // Print the exception and line number
            // where tit occurred
            e.printStackTrace();
        }

    }



    public void runCompressionProgressStats() throws InterruptedException {
        while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
            int progressDone = progressMonitor.getPercentDone();
            System.out.println("Percentage done: " + progressDone);
            System.out.println("Current file: " + progressMonitor.getFileName());
            System.out.println("Current task: " + progressMonitor.getCurrentTask());

            Thread.sleep(300);
        }

        if (progressMonitor.getResult().equals(ProgressMonitor.Result.SUCCESS)) {
            System.out.println("Successfully added files/folder to zip");

        } else if (progressMonitor.getResult().equals(ProgressMonitor.Result.ERROR)) {
            System.out.println("Error occurred. Error message: " + progressMonitor.getException().getMessage());
        } else if (progressMonitor.getResult().equals(ProgressMonitor.Result.CANCELLED)) {
            System.out.println("Task cancelled");
        }
        progressMonitor.endProgressMonitor();//ending multithreaded progress monitor
        zipFile.setRunInThread(false);//ending zipfile's thread

    }




}




