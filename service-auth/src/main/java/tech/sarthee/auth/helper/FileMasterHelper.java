package tech.sarthee.auth.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class FileMasterHelper {

    @Value("${file.storage.path}")
    private String qrFilePath;


}
