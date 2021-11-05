package com.github.gronblack.pm.web.testdata;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.to.PlanetFullTo;
import com.github.gronblack.pm.to.PlanetTo;
import com.github.gronblack.pm.util.PlanetsUtil;
import com.github.gronblack.pm.web.MatcherFactory;

import java.util.List;
import java.util.Set;

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

    public static final Lord lord2 = new Lord(2, "Adam Smith", 58, 182);

    public static final Planet planet1 = new Planet(1, "Agamar", 45985);
    public static final Planet planet2 = new Planet(2, "Ando", 56329);
    public static final Planet planet3 = new Planet(3, "Bogano", 156238);
    public static final Planet planet4 = new Planet(4, "Cato Neimoidia", 120569);
    public static final Planet planet5 = new Planet(5, "Devaron", 85632);
    public static final Planet planet6 = new Planet(6, "Endor", 12698);
    public static final Planet planet7 = new Planet(7, "Fondor", 35962);
    public static final Planet planet8 = new Planet(8, "Hosnian Prime", 74125);
    public static final Planet planet9 = new Planet(9, "Ord Mantell", 62139);
    public static final Planet planet10 = new Planet(10, "Rishi", 48568);
    public static final Planet planet11 = new Planet(11, "Sorgan", 43695);
    public static final Planet planet12 = new Planet(12, "Savareen", 94681);
    public static final Planet planet13 = new Planet(13, "Vandor-1", 37457);
    public static final Planet planet14 = new Planet(14, "Zygerria", 28957);
    public static final Set<Planet> planets = Set.of(planet1, planet2, planet3, planet4, planet5, planet6, planet7, planet8, planet9, planet10, planet12, planet11, planet13, planet14);

    public static final List<PlanetTo> planetTos;
    public static final PlanetFullTo planet2FullTo;

    static {
        planet2.setLord(lord2);
        planetTos = PlanetsUtil.getTos(List.of(planet1, planet2, planet3, planet4, planet5, planet6, planet7, planet8, planet9, planet10, planet12, planet11, planet13, planet14));
        planet2FullTo = PlanetsUtil.createFullTo(planet2);
    }
}
