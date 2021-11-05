package com.github.gronblack.pm.util;

import com.github.gronblack.pm.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class ErrorUtil {
    public static Supplier<NotFoundException> notFound(Class<?> clazz, int id) {
        return () -> new NotFoundException("Not found " + clazz.getSimpleName() + " with id=" + id);
    }
}
