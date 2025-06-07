package ru.plenkkovii.weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    @NotBlank(message = "Заполните поле логин")
    @Size(min = 2, max = 30, message = "Логин должен быть от 2 до 30 символов")
    private String login;

    @NotBlank(message = "Пустое поле с паролем")
    @Size(min = 3, max = 20, message = "Пароль должен быть от 3 до 20 символов")
    private String password1;

    @NotBlank(message = "Повтор пароля обязателен")
    private String password2;
}
