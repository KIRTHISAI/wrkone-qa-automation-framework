package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.AdduserPage;
import pages.UserListPage;

public class UserListSteps extends baseClass {

    UserListPage userList;

    @When("User searches for the created user")
    public void user_searches_for_the_created_user() {

        userList = new UserListPage(driver);

        System.out.println("Searching : " + AdduserPage.generatedName);

        userList.searchUser(AdduserPage.generatedName);
    }

    @And("User opens the user profile")
    public void openUserProfile() {

        userList.openUserProfile(AdduserPage.generatedName);
    }
}