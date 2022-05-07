package uz.hp.hrapp.payload;

import uz.hp.hrapp.entity.enums.Month;
import lombok.Data;

import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SalaryTakenDto {
    @Email
    @NotNull
    private String email;

    @NotNull
    private double amount;

    @Enumerated
    private Month period;
}
