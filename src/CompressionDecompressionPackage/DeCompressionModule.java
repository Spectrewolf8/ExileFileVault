package CompressionDecompressionPackage;

import FilesPackage.FileToDeCompress;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.io.FilenameUtils;

public class DeCompressionModule {

    public void decompressZip(FileToDeCompress fileToProcess, String originFilePathToDecompressTo, String Password) throws ZipException, InterruptedException {
        //new ZipFile(String.valueOf(fileToProcess.getAbsoluteFilePath()), Password.toCharArray()).extractAll(String.valueOf(fileToProcess.getAbsoluteFilePath()).replace(".zip", ""));
        //System.out.println(FilenameUtils.removeExtension(String.valueOf(fileToProcess.getAbsoluteFilePath())));
        //new ZipFile(String.valueOf(fileToProcess.getAbsoluteFilePath())).extractAll(FilenameUtils.removeExtension(String.valueOf(fileToProcess.getAbsoluteFilePath())));//FilenameUtils.removeExtension(fileNameWithExt);

        ZipFile zipFile = new ZipFile(fileToProcess.getFile(), Password.toCharArray());
        zipFile.setRunInThread(true);
        ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
        if (zipFile.isValidZipFile()){
            zipFile.extractAll(FilenameUtils.removeExtension(originFilePathToDecompressTo));//FilenameUtils.removeExtension(fileNameWithExt);

        }else{
            System.out.println("Invaild zip file");
        }


        while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
            System.out.println("Percentage done: " + progressMonitor.getPercentDone());

            System.out.println("Current file: " + progressMonitor.getFileName());
            System.out.println("Current task: " + progressMonitor.getCurrentTask());
            Thread.sleep(200);
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

        System.out.println(originFilePathToDecompressTo);

    }
}
