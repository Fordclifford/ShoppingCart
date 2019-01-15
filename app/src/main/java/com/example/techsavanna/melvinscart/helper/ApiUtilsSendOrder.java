package com.example.techsavanna.melvinscart.helper;

public class ApiUtilsSendOrder {
    private ApiUtilsSendOrder() {}

    public static final String BASE_URL = "http://192.168.1.251/melvinscart/retrofit/POST/";

    public static APIPostOrderService getAPIService() {

        return  PostOrderClient.getClient(BASE_URL).create(APIPostOrderService.class);
    }
}
