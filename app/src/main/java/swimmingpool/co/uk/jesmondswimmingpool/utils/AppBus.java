package swimmingpool.co.uk.jesmondswimmingpool.utils;

import com.squareup.otto.Bus;

/**
 * Created by cody on 2017/11/28.
 */

public class AppBus extends Bus {

    private static AppBus bus;

    public static AppBus getInstance() {
        if (bus == null) {
            bus = new AppBus();
        }
        return bus;
    }

}