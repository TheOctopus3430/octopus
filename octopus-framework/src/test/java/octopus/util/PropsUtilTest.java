package octopus.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class PropsUtilTest {


    @Test
    void loadProps() {
        Properties properties = PropsUtil.loadProps("octopus.properties");
        assertThat(properties).isNotNull();
        String value = PropsUtil.getString(properties, "octopus.framework.jdbc.password");
        assertThat(value).isEqualTo("root");
    }

    @Test
    void getString() {
    }

    @Test
    void testGetString() {
    }
}