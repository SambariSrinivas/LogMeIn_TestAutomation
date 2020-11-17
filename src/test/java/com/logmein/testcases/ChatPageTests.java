package com.logmein.testcases;

import com.logmein.base.TestBase;
import com.logmein.page.ChatPage;
import com.logmein.page.LoginPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChatPageTests extends TestBase {
    static String randomString = RandomStringUtils.random(5, true, true);
    LoginPage loginPage;
    ChatPage chatPage;

    public ChatPageTests() {
        super();
    }

    @BeforeMethod
    public void chatPage_SetUp() {
        loginPage = new LoginPage(driver);
        loginPage.login();
        chatPage = new ChatPage(driver);
    }

    @AfterMethod
    public void login_Out_From_Application() {
        loginPage.logout();
    }

    //verify  Chat page elements
    @Test
    public void verify_ChatPage_Elements() {
        String[] actualElementsTexts = chatPage.verifyAllChatPageElements();
        String[] expectedElementText = {"Groups", "Contacts"};
        Assert.assertEquals(actualElementsTexts, expectedElementText);
    }


    @Test(dataProvider = "CreateGroupData", dataProviderClass = com.logmein.TestData.ChatPageDataProvider.class)
    public void verify_New_Group_Creation(String groupName, String firstUser, String secondUser) {
        String GroupCreationResult = chatPage.createNewGroup(groupName + randomString, firstUser, secondUser);
        Assert.assertEquals(GroupCreationResult, "Group Created Successfully");
    }

    @Test(dataProvider = "JoinGroupData", dataProviderClass = com.logmein.TestData.ChatPageDataProvider.class)
    public void verify_Join_and_Chat_with_Existing_Group(String groupName, String message) {
        String joinGroupResult = chatPage.joinGroup(groupName, message + randomString);
        Assert.assertEquals(joinGroupResult, "Joining and Chat with Group is successful");
    }

    @Test(dataProvider = "DirectChatWithColleagueData", dataProviderClass = com.logmein.TestData.ChatPageDataProvider.class)
    public void verify_Chat_with_Colleague_Directly(String userName, String message) {
        String chatWithUserResult = chatPage.chatWithUser(userName, message + randomString);
        Assert.assertEquals(chatWithUserResult, "Chat with User is Successful");
    }
}
