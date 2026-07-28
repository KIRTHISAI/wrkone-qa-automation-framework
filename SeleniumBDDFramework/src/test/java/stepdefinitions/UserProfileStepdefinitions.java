package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.UserProfilePage;
import pages.UserListPage;
import pages.AdduserPage;

public class UserProfileStepdefinitions extends baseClass {

    UserProfilePage profile;
    UserListPage userListPage;
    
    @And("User clicks Manage Roles")
    public void user_clicks_manage_roles() {
        profile = new UserProfilePage(driver);
        profile.clickManageRoles();
    }

    @And("User assigns QA Role")
    public void user_assigns_qa_role() {
        profile.assignQaRole();
    }

    @And("User clicks Update Roles")
    public void user_clicks_update_roles() {
        profile.clickUpdateRoles();
    }

    @And("User clicks Edit User")
    public void user_clicks_edit_user() {
        profile.clickEditUser();
    }
    @When("User deactivates the user")
    public void user_deactivates_the_user() {
        profile.deactivateUser();
    }
    @And("User confirms deactivation")
    public void user_confirms_deactivation() {
        profile.clickUpdateUser();
    }

    @Then("User status should be Deactivated")
    public void user_status_should_be_Deactivated() {

        userListPage = new UserListPage(driver);

        String userName = AdduserPage.generatedName;

        userListPage.verifyUserStatus(userName);
    }
}