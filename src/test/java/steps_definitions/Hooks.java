package steps_definitions;

import io.cucumber.java.BeforeAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("before all");
        //try {
           // System.out.println(getSecretValue());
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
    }

    public static String getSecretValue() throws IOException, InterruptedException {
        String value;
        String cmd = "gcloud secrets versions access latest --secret=\"dev_viewer-radiologist-user-password\" --project=\"p3002010-dev-4cca-rdl-calantic\"";

        ProcessBuilder builder = new ProcessBuilder();

        if(System.getProperty("os.name").contains("Windows")){
            builder.command("cmd.exe","/c",cmd);
        }else {
            builder.command("/bin/bash","-c",cmd);
        }

        Process result = builder.start();

        int exitCode=result.waitFor();
        System.out.println("Exit code: " + exitCode);
        if (exitCode == 0) {
            System.out.println("Secret key value extracted successfully from GCP Secret manager");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(result.getInputStream()));
            value = stdInput.readLine();
        } else {
            value = "";
            System.out.println("Unable to extract the required secret from the GCP secret manager ");
        }
        return value;
    }

}
