package octopus.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

class ClassUtilTest {

    @Test
    void loadClass() {
        Class<?> octopus = ClassUtil.loadClass("octopus", false);
        Set<Class<?>> octopus1 = ClassUtil.getClassSet("octopus");

    }
}