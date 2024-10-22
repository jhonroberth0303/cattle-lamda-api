package com.unir.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BovineDTO {

    private int id;
    private String name;
    private String gender;
    private String bornDate;
    private String age;
    private String breed;
    private String color;
    private String father;
    private String mother;

    public String getAge() {

        return Stream.ofNullable(bornDate)
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .map(date -> Period.between(date,LocalDate.now()).toTotalMonths())
                .map(num -> num/12+":"+num%12).findFirst().orElse("unknown");

    }

    @Override
    public String toString() {
        return "id:" + id + "-name:" + name + "-age:" + getAge();
    }

}
