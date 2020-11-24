package octopus.util;

import octopus.helper.ConfigHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigHelperTest {

    @Test
    void getDriver() {
        assertThat(ConfigHelper.getDriver()).isNotNull();
    }

    @Test
    void getUrl() {
        assertThat(ConfigHelper.getUrl()).isNotNull();
    }

    @Test
    void getUsername() {
        assertThat(ConfigHelper.getUsername()).isNotNull();
    }

    @Test
    void getPassword() {
        assertThat(ConfigHelper.getPassword()).isNotNull();
    }

    @Test
    void getBasePackage() {
        assertThat(ConfigHelper.getBasePackage()).isNotNull();
    }

    @Test
    void getJspPath() {
        assertThat(ConfigHelper.getJspPath()).isNotNull();
    }

    @Test
    void getAssetPath() {
        assertThat(ConfigHelper.getAssetPath()).isNotNull();
    }
}