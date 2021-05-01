package com.template.sbtemplate.secrets;

import com.template.sbtemplate.pojo.DBCredentials;
import com.template.sbtemplate.pojo.Secrets;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SecretManager implements ISecretManager {

    private Map<String, DBCredentials> dbCredentials;

    public void listFilesUsingJavaIO(String dirName) throws IOException {


        try (Stream<Path> paths = Files.walk(Paths.get("."))) {
            paths.filter(Files::isRegularFile)
                    .forEach(System.out::println);
        }
    }

    @Value(value = "${secrets.path.file}")
    private String filePath;

    @PostConstruct
    void loadSecrets() throws IOException {

        try (InputStream stream = new FileInputStream(filePath)) {
            Secrets secrets = new ObjectMapper().readValue(stream, Secrets.class);
            dbCredentials = secrets.getDbCredentials()
                .stream()
                .collect(Collectors.toMap(DBCredentials::getName, dbCredential -> dbCredential));
        } catch (FileNotFoundException e) {
            System.out.println("list directory only " +  Path.of(".").toAbsolutePath().toString());
            listFilesUsingJavaIO(".");
            System.out.println("file path" + new File("scores.dat").getAbsolutePath() + " " + new File("scores.dat"));
            throw new FileNotFoundException("secret_file does not exists: " + filePath + e.toString());
        }
    }

    @Override
    public DBCredentials getDBCredentials(String dbname) {
        return dbCredentials.get(dbname);
    }
}
