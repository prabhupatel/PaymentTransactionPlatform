package com.hignnote.payments.fileTypeParser;

import com.hignnote.payments.exception.AuthorizationException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FileReader {
     boolean read(String fileName) throws IOException, URISyntaxException, AuthorizationException;
}
