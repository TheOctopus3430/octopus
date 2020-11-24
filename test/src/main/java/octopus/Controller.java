package octopus;


import octopus.annotation.ZAction;
import octopus.annotation.ZController;
import octopus.web.Data;
import octopus.web.Param;

import java.util.Map;

@ZController
public class Controller {

    @ZAction("get:/user")
    public Data user(Param param) {
        Map<String, Object> paramMap = param.getParamMap();
        return new Data(paramMap);
    }
}
