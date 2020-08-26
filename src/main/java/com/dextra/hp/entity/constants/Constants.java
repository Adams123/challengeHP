package com.dextra.hp.entity.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    @Value("${baseUrl}")
    public String BASE_URL;

    public static final String dbIdentifier = "DEXTRA DB"; //TODO get from connection settings or deploy env

}
