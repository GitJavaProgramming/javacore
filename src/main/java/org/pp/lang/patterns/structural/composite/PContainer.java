package org.pp.lang.patterns.structural.composite;

import java.awt.*;
import java.util.ArrayList;

public class PContainer extends Component {

    private java.util.List<Component> componentList;

    public PContainer() throws HeadlessException {
        componentList = new ArrayList<>();
//        requestFocus();
    }

    public PContainer add(Component component) {
        componentList.add(component);
        return this;
    }

    public void printSelf() {
        System.out.println(componentList);
    }
}
