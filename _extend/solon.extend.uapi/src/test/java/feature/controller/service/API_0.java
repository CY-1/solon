package feature.controller.service;

import org.noear.solon.annotation.XMapping;
import org.noear.solon.extend.uapi.UapiCode;

public class API_0 {
    @XMapping
    public Object call() {
        return new UapiCode(999, "The api does not exist");
    }
}
