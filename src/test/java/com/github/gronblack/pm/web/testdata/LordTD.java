package com.github.gronblack.pm.web.testdata;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.web.MatcherFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LordTD {
    public static final MatcherFactory.Matcher<Lord> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Lord.class, "planets");
    public static final MatcherFactory.Matcher<Lord> WITH_PLANETS_MATCHER =
            MatcherFactory.usingAssertions(Lord.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final Lord lord1 = new Lord(1, "Nius Maximus", 52);
    public static final Lord lord2 = new Lord(2, "Adam Smith", 58);
    public static final Lord lord3 = new Lord(3, "Eleonora Kirimova", 72);
    public static final Lord lord4 = new Lord(4, "Gron Black", 33);
    public static final Lord lord5 = new Lord(5, "Ferdinand Porsche", 62);
    public static final Lord lord6 = new Lord(6, "Mark Strong", 38);
    public static final Lord lord7 = new Lord(7, "Robert B. Weide", 80);
    public static final Lord lord8 = new Lord(8, "Ronny Coleman", 25);
    public static final Lord lord9 = new Lord(9, "Sam Fisher", 42);
    public static final Lord lord10 = new Lord(10, "Mick Jagger", 50);
    public static final Lord lord11 = new Lord(11, "Klaus Meine", 55);
    public static final Lord lord12 = new Lord(12, "Nikita Abramov", 48);

    public static final Planet planet8 = new Planet(8, "Hosnian Prime");
    public static final Planet planet9 = new Planet(9, "Ord Mantell");
    public static final Planet planet10 = new Planet(10, "Rishi");

    static {
        lord7.setPlanets(new HashSet<>(List.of(planet8, planet9, planet10)));
    }
}
