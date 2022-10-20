package com.example.application.components.appnav;


import com.fasterxml.jackson.databind.util.ClassUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ProductControllerTest {

    private final RestTemplate rest = new RestTemplate();

    public ProductDto[] getProduct() {
        ProductDto[] response = rest.getForObject("http://localhost:7004/product", ProductDto[].class);
        return response;
    }

    public ProductDto[] postProduct() {
        ProductDto[] response = rest.getForObject("http://localhost:7004/product", ProductDto[].class);
        return response;
    }

    public void putProduct(long id) {
        String uri = "http://localhost:7004/product/" + id;
        ProductDto requestBody = new ProductDto();
        rest.put(uri, requestBody);
    }

    public void test() {
        //Сформировали URL
        String uri = "http://localhost:7004/manufacturer";
        /**
         * Cформировали параметры в url:
         * например в http://somesite.ru/home/my?id=2&name=kitty
         * http://somesite.ru/home/my - это url
         * id=2&name=kitty - это uri params.
         * То есть все что идет после вопросительного знака.
         * Пары ключ значения в uri params разделяются символом &
         */
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("id", "2");
        uriParams.put("name", "kitty");
        ;
        /**
         * Формируем тело запроса в соответсвии с документацией на метод.
         * Предположим, что это {@link ProductDto}
         **/
        CreateManufacturerDto requestBody = new CreateManufacturerDto("Man2", "logo2");

        //Формируем экземляр класса HttpEntity
        HttpEntity<CreateManufacturerDto> httpEntity = new HttpEntity<>(requestBody);

        //Формируем заголовки (headers), которые собой тоже представляют пары ключ-знначение
        //Map<String, List<String>> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json");
        //headers.put("Authorization", "Bearer xfdfdjsdyfggcbwefgbwhvmiqhdjsnn")
//        httpEntity.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        httpEntity.getHeaders().setBearerAuth("xfdfdjsdyfggcbwefgbwhvmiqhdjsnn");

        //Предположим запрашиваемы метод должен вернуть обьект с типом ProductDto.
        //Получим класс
        Class<Void> responseObjectClass = Void.class;

        //Отправляем запрос на сервер и полученнный ответ записываем в переменную responseObj
        ResponseEntity<Void> responseObj = rest.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                responseObjectClass
//                ,
//                uriParams - отсутсвуют в данном запросе
        );

        //Выведем на экран составляющие ответа. (Response Status Code)
        System.out.printf(
                """
                STATUS CODE: %s
                STATUS PHRASE: %s
                """
                ,
                responseObj.getStatusCodeValue(), //Численное значение статуса ответа
                responseObj.getStatusCode().getReasonPhrase() //Это его короткая текстовая фраза
        );

        //Выведем на экран заголовки ответа (Response Headers)
        responseObj.getHeaders().keySet().forEach(key -> {
            System.out.println("Key: " + key + " - Value: " + responseObj.getHeaders().get(key));
        });

        //Выведем тело ответа на экран (Response Body)
        System.out.println("Body: " + responseObj.getBody());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDto {
        long id;
        String name;
        int price;
        float volume;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreateManufacturerDto {
        String name;
        String logo;
    }
}
