package com.hignnote.payments.processor;

import com.hignnote.payments.exception.AuthorizationException;
import com.hignnote.payments.factory.FileReaderFactory;
import com.hignnote.payments.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.TreeMap;

import com.hignnote.payments.fileTypeParser.FileReader;

@Slf4j
@Controller
public class Orchestrator {

    public boolean start(String fileName)  {
        try {
            Boolean isFileExists = FileUtil.isFileExist(fileName);
            if (!isFileExists) {
                log.error("file not found");
                return Boolean.FALSE;
            }
            FileReader fileReader = FileReaderFactory.getFileReader(fileName);
            return fileReader.read(fileName);
        }catch (AuthorizationException | FileNotFoundException ex) {
            log.error("exception occurred {} ",ex);
        } catch (IOException e) {
            log.error("IOException {}",e.getMessage());
        } catch (URISyntaxException e) {
            log.error("URISyntaxException {}",e.getMessage());
        }
        return Boolean.FALSE;
    }
}
