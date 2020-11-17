package com.logmein.page;

import com.logmein.utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ChatPage {
    protected WebDriver driver;
    public Utilities util;

    @FindBy(xpath = "//a[@title='Chats']")
    WebElement Chats;

    @FindBy(xpath = "//span[normalize-space()='Groups']")
    WebElement Groups;

    @FindBy(xpath = "//button[@aria-label='Find or create']")
    WebElement FindOrCreateGroupButton;

    @FindBy(xpath = "//span[normalize-space()='Contacts']")
    WebElement Contacts;

    @FindBy(xpath = "//button[@aria-label='Start a conversation']")
    WebElement StartConversation;


    //Create Group Elements
    @FindBy(xpath = "//button[normalize-space()='New group']")
    WebElement NewGroupButton;
    @FindBy(xpath = "//input[@placeholder='e.g. Team Awesome']")
    WebElement GroupNameElement;
    @FindBy(xpath = "//div[contains(text(),'Search by name or email')]")
    WebElement SelectGroupMembers;
    @FindBy(xpath = "//div[@class='react-select__input']")
    WebElement ReactSelectInput;
    @FindBy(xpath = "//label[contains(text(),'Public')]")
    WebElement PublicGroupButton;
    @FindBy(xpath = "//label[contains(text(),'Private')]")
    WebElement PrivateGroupButton;
    @FindBy(xpath = "//button[normalize-space()='Create group']")
    WebElement CreateGroupButton;
    @FindBy(xpath = "//i[@class='togo-icon togo-icon-settings togo-icon-lg']")
    WebElement ChatSettings;

    //WebElements required for joinGroup method
    @FindBy(xpath = "//input[@placeholder='Search groups']")
    WebElement SearchGroup;

    //WebElement required for chatWithUser group
    @FindBy(xpath = "//input[@placeholder='Type a name or email address']")
    WebElement SearchUser;

    @FindBy(xpath = "//div[@class='public-DraftStyleDefault-block public-DraftStyleDefault-ltr']")
    WebElement ChatBox;


    //Initialize Home Page Objects

    public ChatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        util = new Utilities(driver);
    }

    //Page Actions

    public String[] verifyAllChatPageElements() {
        util.click(Chats);
        return new String[]{util.getInnerText(Groups), util.getInnerText(Contacts)};
    }

    //Create a new Group
    public String createNewGroup(String groupName, String firstUser, String secondUser) {
        util.click(Chats);
        util.click(FindOrCreateGroupButton);
        util.click(NewGroupButton);
        util.inputText(GroupNameElement, groupName);
        //util.actionMoveAndClick(SelectGroupMembers, firstUser, secondUser);
        util.actionMoveAndClick(ReactSelectInput, firstUser, secondUser);
        util.click(PublicGroupButton);
        util.click(CreateGroupButton);
        WebElement NewGroupName = driver.findElement(By.xpath("//span[normalize-space()='" + groupName + "']"));
        util.click(NewGroupName);
        WebElement GroupHeading = driver.findElement(By.xpath("//input[@value='" + groupName + "']"));
        if (util.getInnerText(NewGroupName).equals(GroupHeading.getAttribute("value"))) {

            System.out.println("Group Name and Group Heading are equal...Checking Group Members");
            WebElement GroupMembers = driver.findElement(By.xpath("//button[@class='group-member-list btn btn-link']"));
            util.click(GroupMembers);
            WebElement FirstUser = driver.findElement(By.xpath("//span[normalize-space()='" + firstUser + "']"));
            WebElement SecondUser = driver.findElement(By.xpath("//span[normalize-space()='" + secondUser + "']"));
            WebElement GroupCreator = driver.findElement(By.xpath("//span[normalize-space()='User 2 (you)']"));
            if (FirstUser.isDisplayed() && SecondUser.isDisplayed() && GroupCreator.isDisplayed()) {
                System.out.println("Group Created Successfully with required members ... Group Deletion is in progress");
                deleteGroup(groupName);
                return "Group Created Successfully";

            } else {
                return "Group partially Created";
            }
        } else {
            return "Group Creation Failed";
        }
    }

    //Delete a Group
    public void deleteGroup(String groupName) {

        WebElement NewGroupName = driver.findElement(By.xpath("//span[normalize-space()='" + groupName + "']"));
        util.click(NewGroupName);
        util.click(ChatSettings);
        WebElement DeleteGroup = driver.findElement(By.xpath("//button[normalize-space()='Delete Group']"));
        DeleteGroup.click();
        System.out.println("Group Deletion has Success");

    }

    //Join existing Group
    public String joinGroup(String groupName, String message) {
        util.click(Chats);
        util.click(FindOrCreateGroupButton);
        util.actionMoveAndClick(SearchGroup, groupName);
        driver.findElement(By.xpath("//div[contains(text(),'" + groupName + "')]")).click();
        WebElement NewGroupName = driver.findElement(By.xpath("//div[@class='group-name']//span[contains(text(),'" + groupName + "')]"));
        if (util.getInnerText(NewGroupName).contentEquals(groupName)) {
            System.out.println("Group Joined Successfully...........Starting Conversation with Group");
            util.inputText(ChatBox, message);
            driver.findElement(By.xpath("//button[@aria-label='Submit']//img")).click();
            WebElement Message = driver.findElement(By.xpath("//p[normalize-space()='" + message + "']"));
            if (Message.getText().contentEquals(message)) {
                System.out.println("sending messages to group is success...Leaving the Group");
                leaveFromGroup(groupName);
                return "Joining and Chat with Group is successful";
            } else {
                return "Chat with Group is Unsuccessful";
            }

        } else {
            return "Joining into a Group is Failed";
        }
    }

    //Leave existing group
    public void leaveFromGroup(String groupName) {
        System.out.println("Leaving the Group " + groupName);
        WebElement NewGroupName = driver.findElement(By.xpath("//span[normalize-space()='" + groupName + "']"));
        util.click(NewGroupName);
        util.click(ChatSettings);
        WebElement LeaveGroup = driver.findElement(By.xpath("//button[normalize-space()='Leave Group']"));
        LeaveGroup.click();
        System.out.println("Left the Group Successfully");

    }


    //Chat with User directly
    public String chatWithUser(String userName, String message) {
        util.click(Chats);
        util.click(StartConversation);
        util.actionMoveAndClick(SearchUser, userName);
        driver.findElement(By.xpath("//div[contains(text(),'" + userName + "')]")).click();
        WebElement User = driver.findElement(By.xpath("//*[@data-testid='room-name']"));
        if (util.getInnerText(User).contentEquals(userName)) {
            System.out.println("User Added Successfully To Contacts Section...........Starting Conversation with User");
            User.click();
            util.inputText(ChatBox, message);
            driver.findElement(By.xpath("//button[@aria-label='Submit']//img")).click();
            WebElement Message = driver.findElement(By.xpath("//p[normalize-space()='" + message + "']"));
            if (util.getInnerText(Message).contentEquals(message)) {
                System.out.println("sending messages to User Directly is success...Removing the Chat");
                removeChatWithUser(userName);
                return "Chat with User is Successful";
            } else {
                return "Chat with User is Failed";
            }

        } else {
            return "User addition to contacts section is Failure";
        }
    }


    public void removeChatWithUser(String userName) {
        System.out.println("Removing Chat with User " + userName);
        WebElement User = driver.findElement(By.xpath("//*[@data-testid='room-name']"));
        if (util.getInnerText(User).contentEquals(userName)) {
            User.click();
            driver.findElement(By.xpath("//button[@class='btn-icon remove-button']/i")).click();
            System.out.println(userName + " has removed successfully");
        }
    }
}
