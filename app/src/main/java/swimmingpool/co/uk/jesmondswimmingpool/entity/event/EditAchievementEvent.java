package swimmingpool.co.uk.jesmondswimmingpool.entity.event;

import java.util.ArrayList;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;

/**
 * Created by cody on 2017/11/29.
 */

public class EditAchievementEvent {
    private ArrayList<Courseitem> items;

    public EditAchievementEvent(ArrayList<Courseitem> items) {
        this.items = items;
    }

    public ArrayList<Courseitem> getItems() {
        return items;
    }

    public void setItems(ArrayList<Courseitem> items) {
        this.items = items;
    }
}
