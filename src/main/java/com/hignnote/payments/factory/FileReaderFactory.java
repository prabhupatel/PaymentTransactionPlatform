package com.hignnote.payments.factory;

import com.hignnote.payments.enums.FileType;
import com.hignnote.payments.fileTypeParser.FileReader;
import com.hignnote.payments.fileTypeParser.PdfFileReader;
import com.hignnote.payments.fileTypeParser.TextFileReader;
import com.hignnote.payments.fileTypeParser.XlsFileReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Slf4j
public class FileReaderFactory {

    public static FileReader getFileReader(String fileName) throws IOException {
        String fileExt = FilenameUtils.getExtension(fileName);
        switch (FileType.byValue(fileExt)){
            case TXT:
                return new TextFileReader();
            case PDF:
                return new PdfFileReader();
            case XLS:
                return new XlsFileReader();
            default:
                log.error("Unknown file type {} ",fileExt);
        }
        return null;
    }
}
