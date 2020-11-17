package com.logmein.TestData;

import com.logmein.base.TestBase;
import com.logmein.utils.ExcelUtil;
import org.testng.annotations.DataProvider;

import java.io.FileNotFoundException;

public class ChatPageDataProvider extends ExcelUtil {

    @DataProvider(name = "CreateGroupData")
    public Object[][] create_Group_Data() throws FileNotFoundException {
        return readExcelData(TestBase.prop.getProperty("createNewGroupTestDataSheet"));
    }

    @DataProvider(name = "JoinGroupData")
    public Object[][] join_Group_Data() throws FileNotFoundException {
        return readExcelData(TestBase.prop.getProperty("joinGroupTestDataSheet"));
    }

    @DataProvider(name = "DirectChatWithColleagueData")
    public Object[][] direct_Chat_With_Colleague_Data() throws FileNotFoundException {
        return readExcelData(TestBase.prop.getProperty("directChatTestDataSheet"));
    }
}
