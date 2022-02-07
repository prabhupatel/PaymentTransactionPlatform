package com.hignnote.payments.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileUtil {
    public static Boolean isFileExist(String fileName) throws FileNotFoundException {
        return new File(fileName).exists();
    }


    public static Stream<String> getFileFromResourceAsStream(String fileName) throws IOException, URISyntaxException {
        return new BufferedReader(new FileReader(fileName)).lines();
    }
}
