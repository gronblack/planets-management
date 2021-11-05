package com.github.gronblack.pm.util;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.to.LordFullTo;
import com.github.gronblack.pm.to.LordTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.gronblack.pm.util.ErrorUtil.notFound;

@UtilityClass
public class LordsUtil {
    public static List<LordTo> getTos(Collection<Lord> lords) {
        return lords.stream()
                .map(LordsUtil::createTo)
                .collect(Collectors.toList());
    }

    public static LordTo createTo(Lord lord) {
        return new LordTo(lord.getId(), lord.getName(), lord.getAge());
    }

    public static LordFullTo createFullTo(Lord lord) {
        return new LordFullTo(lord.getId(), lord.getName(), lord.getAge(), lord.getHeight(), PlanetsUtil.getTos(lord.getPlanets()));
    }

    public static Optional<LordFullTo> createFullToOptional(Optional<Lord> lordOptional, int id) {
        return Optional.of(createFullTo(lordOptional.orElseThrow(notFound(Lord.class, id))));
    }
}
