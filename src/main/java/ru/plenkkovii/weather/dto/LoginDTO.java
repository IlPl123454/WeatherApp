package ru.plenkkovii.weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Заполните поле логин")
    @Size(min = 2, max = 30, message = "Напоминание. Логин должен быть от 2 до 30 символов")
    private String login;

    @NotBlank(message = "Пустое поле с паролем")
    @Size(min = 3, max = 20, message = "Напоминание. Пароль должен быть от 3 до 20 символов")
    private String password;
}
