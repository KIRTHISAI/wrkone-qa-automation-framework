package stepdefinitions;

import base.baseClass;
import io.cucumber.java.After;

public class Hooks extends baseClass {

    @After
    public void tearDown() {

        closeBrowser();
    }
}