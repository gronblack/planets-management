package com.github.gronblack.pm.web.testdata;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.to.PlanetFullTo;
import com.github.gronblack.pm.to.PlanetTo;
import com.github.gronblack.pm.util.PlanetsUtil;
import com.github.gronblack.pm.web.MatcherFactory;

import java.util.List;
import java.util.Set;

import static com.github.gronblack.pm.web.testdata.LordTD.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanetTD {
    public static final MatcherFactory.Matcher<Planet> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Planet.class, "lord");
    public static final MatcherFactory.Matcher<PlanetTo> MATCHER_TO = MatcherFactory.usingEqualsComparator(PlanetTo.class);
    public static final MatcherFactory.Matcher<PlanetFullTo> WITH_LORD_MATCHER =
            MatcherFactory.usingAssertions(PlanetFullTo.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final Lord lord2 = new Lord(2, "Adam Smith", 58);

    public static final Planet planet1 = new Planet(1, "Agamar");
    public static final Planet planet2 = new Planet(2, "Ando");
    public static final Planet planet3 = new Planet(3, "Bogano");
    public static final Planet planet4 = new Planet(4, "Cato Neimoidia");
    public static final Planet planet5 = new Planet(5, "Devaron");
    public static final Planet planet6 = new Planet(6, "Endor");
    public static final Planet planet7 = new Planet(7, "Fondor");
    public static final Planet planet8 = new Planet(8, "Hosnian Prime");
    public static final Planet planet9 = new Planet(9, "Ord Mantell");
    public static final Planet planet10 = new Planet(10, "Rishi");
    public static final Planet planet11 = new Planet(11, "Sorgan");
    public static final Planet planet12 = new Planet(12, "Savareen");
    public static final Planet planet13 = new Planet(13, "Vandor-1");
    public static final Planet planet14 = new Planet(14, "Zygerria");
    public static final Set<Planet> planets = Set.of(planet1, planet2, planet3, planet4, planet5, planet6, planet7, planet8, planet9, planet10, planet12, planet11, planet13, planet14);

    public static final List<PlanetTo> planetTos;
    public static final PlanetFullTo planet2FullTo;

    static {
        planet1.setLord(lord2);
        planet2.setLord(lord2);
        planet3.setLord(lord1);
        planet4.setLord(lord3);
        planet5.setLord(lord4);
        planet6.setLord(lord5);
        planet7.setLord(lord6);
        planet8.setLord(lord7);
        planet9.setLord(lord7);
        planet10.setLord(lord7);
        planet11.setLord(lord8);
        planet12.setLord(lord5);
        planet13.setLord(lord1);

        planetTos = PlanetsUtil.getTos(List.of(planet1, planet2, planet3, planet4, planet5, planet6, planet7, planet8, planet9, planet10, planet12, planet11, planet13, planet14));
        planet2FullTo = PlanetsUtil.createFullTo(planet2);
    }
}
