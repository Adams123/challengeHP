package com.dextra.hp.entity.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    @Value("${baseUrl}")
    public String baseUrl;

    public static final String DB_IDENTIFIER = "DEXTRA DB"; //TODO get from connection settings or deploy env

}
