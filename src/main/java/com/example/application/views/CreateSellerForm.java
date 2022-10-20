package com.example.application.views;

import com.example.application.components.appnav.ProductControllerTest;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

public class CreateSellerForm extends FormLayout {

    private TextField nameTF;
    private TextField phoneNumberTF;
    private PasswordField passwordFieldPF;
    private TextField productNameTF;
    private MultiSelectComboBox cityTF;
    private Button saveButton;

    public SellerDto[] getSellers(){
        var rest = new RestTemplate();
        SellerDto[] response = rest.getForObject("http://localhost:7004/seller", SellerDto[].class);
        return response;
    }

    public CreateSellerForm() {
        nameTF = new TextField("Введите имя");
        phoneNumberTF = new TextField("Номер телефона");
        productNameTF = new TextField("Название продукта");
        cityTF = new MultiSelectComboBox("Введите Город");
        passwordFieldPF = new PasswordField("Пароль");
        saveButton = new Button("Save");
        saveButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                ProductControllerTest test = new ProductControllerTest();
                test.test();
            }
        });
        add(nameTF, phoneNumberTF, productNameTF,cityTF,passwordFieldPF,saveButton);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerDto {
        long id;
        String name;
        String inn;
        String bik;
        String description;
    }

}
