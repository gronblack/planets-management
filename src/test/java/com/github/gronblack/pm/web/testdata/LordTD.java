package com.github.gronblack.pm.web.testdata;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.to.LordFullTo;
import com.github.gronblack.pm.to.LordTo;
import com.github.gronblack.pm.util.LordsUtil;
import com.github.gronblack.pm.web.MatcherFactory;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LordTD {
    public static final MatcherFactory.Matcher<Lord> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Lord.class, "planets");
    public static final MatcherFactory.Matcher<LordTo> MATCHER_TO = MatcherFactory.usingIgnoringFieldsComparator(LordTo.class, "planets");
    public static final MatcherFactory.Matcher<LordFullTo> WITH_PLANETS_MATCHER =
            MatcherFactory.usingAssertions(LordFullTo.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final Lord lord1 = new Lord(1, "Nius Maximus", 52, 175);
    public static final Lord lord2 = new Lord(2, "Adam Smith", 58, 182);
    public static final Lord lord3 = new Lord(3, "Eleonora Kirimova", 72, 180);
    public static final Lord lord4 = new Lord(4, "Gron Black", 33, 174);
    public static final Lord lord5 = new Lord(5, "Ferdinand Porsche", 62, 168);
    public static final Lord lord6 = new Lord(6, "Mark Strong", 38, 176);
    public static final Lord lord7 = new Lord(7, "Robert B. Weide", 80, 185);
    public static final Lord lord8 = new Lord(8, "Ronny Coleman", 25, 190);
    public static final Lord lord9 = new Lord(9, "Sam Fisher", 42, 165);
    public static final Lord lord10 = new Lord(10, "Mick Jagger", 50, 178);
    public static final Lord lord11 = new Lord(11, "Klaus Meine", 55, 172);
    public static final Lord lord12 = new Lord(12, "Nikita Abramov", 48, 180);
    public static final List<Lord> lords;

    public static final List<LordTo> lordTos;
    public static final LordFullTo lord7FullTo;

    public static final Planet planet8 = new Planet(8, "Hosnian Prime", 74125);
    public static final Planet planet9 = new Planet(9, "Ord Mantell", 62139);
    public static final Planet planet10 = new Planet(10, "Rishi", 48568);

    static {
        lord7.setPlanets(Set.of(planet8, planet9, planet10));
        lord7FullTo = LordsUtil.createFullTo(lord7);
        lordTos = LordsUtil.getTos(List.of(lord2, lord3, lord5, lord4, lord11, lord6, lord10, lord12, lord1, lord7, lord8, lord9));
        lords = List.of(lord2, lord3, lord5, lord4, lord11, lord6, lord10, lord12, lord1, lord7, lord8, lord9);
    }
}
