package com.si_pmu.pmu.factories;

import com.si_pmu.pmu.models.Partant;

import java.time.LocalDate;

import static com.si_pmu.pmu.factories.TestUtils.randomInt;
import static com.si_pmu.pmu.factories.TestUtils.randomString;

public class PartantFactory {

    public static Partant mockPartant(final int numero) {
        return Partant.builder()
                .date(LocalDate.now())
                .nom(randomString("nom"))
                .numero(numero)
                .build();
    }
}
