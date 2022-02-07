package com.hignnote.payments.fileTypeParser;

import com.hignnote.payments.constants.TransConstant;
import com.hignnote.payments.enums.MessageType;
import com.hignnote.payments.enums.TransactionType;
import com.hignnote.payments.exception.AuthorizationException;
import com.hignnote.payments.util.FileUtil;
import com.hignnote.payments.util.TransactionUtil;
import com.hignnote.payments.util.ValidationUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.hignnote.payments.constants.TransConstant.*;

@Data
@NoArgsConstructor
@Slf4j
public class TextFileReader implements FileReader {

    @Override
    public boolean read(String fileName) throws IOException, URISyntaxException {
        //read string from file & convert it in domain object
        log.info("*****************************START************************************");
        Stream<String> stringStream = FileUtil.getFileFromResourceAsStream(fileName);
        stringStream.forEach(record -> {
                    String response;
                    Map<String, String> dataMap = parse(record);
                    if (!isValidMessage(dataMap)) {
                        response = getResponse(dataMap, TransactionType.ER.name());
                    } else {
                        response = getResponse(dataMap, ValidationUtil.isValidTxn(dataMap) ? TransactionType.OK.name() : TransactionType.DE.name());
                    }
                    log.info(response);
                }
        );
        log.info("*****************************END************************************");
        return Boolean.TRUE;

    }


    private Map<String, String> parse(String input) {
        Map<String, String> dataMap = new HashMap<>();
        try {

            String messageTypeIndicator = input.substring(0, 4);
            dataMap.put("MessageType", MessageType.byValue(messageTypeIndicator).name());

            String bitmap = input.substring(4, 6);
            char[] setBitsArr = TransactionUtil.toBinary(bitmap);
            dataMap.put("bitmap", bitmap);

            input = input.substring(6); // without messageTypeIndicator and bitmap value

            //when only pan passed messageTypeIndicator and bitmap
            if (input.length() >= 14 && input.length() <= 19) {
                dataMap.put("pan", input);
                return dataMap;
            }

            if (setBitsArr[ZIP_IDX] == '1') {    // is bit 6 i.e. ZIP is set
                input = extractZipAndSetInMap(input, dataMap);
            }
            if (setBitsArr[NAME_IDX] == '1') { // is bit 5 i.e. name is set
                input = extractNameAndSetInMap(input, dataMap);
            }
            if (setBitsArr[TransConstant.AMT_IDX] == '1') { // is bit 3 i.e. amt set
                input = extractAmountAndSetInMap(input, dataMap);
            }
            if (setBitsArr[EXP_IDX] == '1') { // is bit 2 i.e. exp date is set
                input = extractExpiryAndSetInMap(input, dataMap);
            }

            if (input.length() >= 14) //set when len is 14 or above (max is 19)
                dataMap.put("pan", input); //rest string is the pan num
        } catch (Exception exception) {
            new AuthorizationException(exception.getMessage());
        }
        return dataMap;
    }

    private String extractExpiryAndSetInMap(String input, Map<String, String> dataElementMap) {
        String exp = input.substring(input.length() - 4);  // length of exp date is 4 chars
        dataElementMap.put("exp", exp);
        return input.substring(0, input.length() - 4);
    }

    private String extractAmountAndSetInMap(String input, Map<String, String> dataElementMap) {
        String amt = input.substring(input.length() - 10);
        dataElementMap.put("amt", amt);
        return input.substring(0, input.length() - 10);
    }

    private String extractNameAndSetInMap(String input, Map<String, String> dataElementMap) {
        int j = input.length() - 1;
        while (Character.isAlphabetic(input.charAt(j))
                || Character.isSpaceChar(input.charAt(j))) {
            j--;
        }
        dataElementMap.put("name", input.substring(j + 1)); // name starting idx is j+1 as j is not a alphabet
        input = input.substring(0, j + 1);
        dataElementMap.put("lenOfName", input.substring(input.length() - 2)); // last 2 digits of input str

        return input.substring(0, input.length() - 2);
    }

    private String extractZipAndSetInMap(String input, Map<String, String> dataElementMap) {
        String zip = input.substring(input.length() - 5);
        dataElementMap.put("zip", zip);
        return input.substring(0, input.length() - 5); //returns the updated string i.e. without zip
    }

    private boolean isValidMessage(Map<String, String> dataElementMap) {
        //check for required data elements
        if (!dataElementMap.containsKey("pan")
                || !dataElementMap.containsKey("exp")
                || !dataElementMap.containsKey("amt")) {
            return false;
        }
        return true;
    }


    private boolean hasKeyAndValueIsNotNullOrEmpty(Map<String, String> dataElementMap, String key) {
        return dataElementMap.containsKey(key)
                && dataElementMap.get(key) != null
                && dataElementMap.get(key).length() > 0;

    }

    private String getResponse(Map<String, String> dataMap, String responseCode) {

        StringBuilder response = new StringBuilder();
        char[] setBitsArr = TransactionUtil.toBinary(dataMap.get("bitmap"));
        setBitsArr[3] = '1'; // set for response code
        String responseBitMap = TransactionUtil.toHex(new String(setBitsArr));

        response.append(MessageType.RESPONSE.getValue());
        response.append(responseBitMap);
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "pan") ? dataMap.get("pan") : "");
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "exp") ? dataMap.get("exp") : "");
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "amt") ? dataMap.get("amt") : "");
        response.append(responseCode);
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "lenOfName") ? dataMap.get("lenOfName") : "");
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "name") ? dataMap.get("name") : "");
        response.append(hasKeyAndValueIsNotNullOrEmpty(dataMap, "zip") ? dataMap.get("zip") : "");

        return response.toString();
    }


}
