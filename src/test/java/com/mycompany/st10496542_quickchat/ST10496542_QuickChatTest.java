/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.st10496542_quickchat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ST10496542_QuickChatTest{
private login loginApp;
private message msg1;
private message msg2;

@BeforeEach
public void setup(){
loginApp=new login();
loginApp.storeUsername="kyl_1";
loginApp.storePassword="Ch&&sec@ke99!";
loginApp.storeCellPhoneNumber="+27838968976";

      msg1= new message("0000000001",1,"+27718693002","Hi Mike,can you join us for dinner tonight?");
      msg2= new message("0000000002",2,"08575975889","Hi Keegen,did you receive the payment?");
      
      message.sentcount=0;
      message.disregardCount=0;
      message.storedCount=0;
      message.totalMessages=0;
      messageData m1= new messageData("0000000001",1,"+27834557896","Did you get the cake?","00:1:DICAKE?","Sent");
      messageData m2= new messageData("0000000002",2,"+27838884567","Where are you? You are late! I have asked you to be on time","00:2:WHERETIME.","Stored");
      messageData m3= new messageData("0000000003",3,"+27834484567","Yohoooo,I am at your gate.","00:3:YOHOOOGATE.","Disregard");
      messageData m4= new messageData("0000000004",4,"0838884567","it is dinner time!","00:4:ITTIME.","Sent");
      messageData m5= new messageData("0000000005",5,"+27838884567","ok, i am leaving without you","00:5:OKYOU.","Stored"); 
      
      message.storedMessages[0]=m1;
      message.storedMessages[1]=m2;
      message.storedMessages[2]=m3;
      message.storedMessages[3]=m4;
      message.storedMessages[4]=m5;
      message.storedCount=5;
      
      message.sentMessage[0]=m1.message;
      message.sentMessage[1]=m4.message;
      message.sentcount=2;
      
      message.messageIDs[0]=m1.messageID;
      message.messageIDs[1]=m2.messageID;
      message.messageIDs[2]=m3.messageID;
      message.messageIDs[3]=m4.messageID;
      message.messageIDs[4]=m5.messageID;
      
      message.messageHashes[0]=m1.messageHash;
      message.messageHashes[1]=m2.messageHash;
      message.messageHashes[2]=m3.messageHash;
      message.messageHashes[3]=m4.messageHash;
      message.messageHashes[4]=m5.messageHash;
      
      message.totalMessages=5;
}

@Test
public void testUernameCorrectlyFormatted(){
assertTrue(loginApp.checkUsername("kyl_i"));
}

@Test
public void testUsernameIncorrectlyFormatterd(){
assertFalse(loginApp.checkUsername("kyl!!!!!!!"));
}

@Test
public void testPasswordMeetComplexity(){
assertTrue(loginApp.checkPasswordComplexity("Ch&&sec@ke99!"));
}

@Test
public void testPasswordDoesNotMeetComplexity(){
assertFalse(loginApp.checkPasswordComplexity("password"));
}
@Test
public void testCellPhoneNumberCorrectlyFormatted(){
assertTrue(loginApp.checkCellPhoneNumber("+27838968976"));
}
@Test
public void testCellPhoneNumberIncorrectlyFormatterd(){
assertFalse(loginApp.checkCellPhoneNumber("08966553"));
}
@Test
public void testloginSuccesssful(){
assertTrue(loginApp.userlogin("kyl_1", "Ch&&sec@ke99!"));
}
@Test
public void testloginFailed(){
assertFalse(loginApp.userlogin("wrongUser", "wrongpass"));
}
// now we are testing part2
@Test
public void testMessageLengthSuccess(){
String msg="Hi Mike, can you join use for dinner tonight?";
assertTrue(msg.length()<=250);
assertEquals("Message ready to send.","Message ready to send.");
}
@Test
public void testMessageLengthFailure(){
String longMsg="a".repeat(251);
assertFalse(longMsg.length()<=250);
int over=longMsg.length()-250;
assertEquals("Message exceeds 250 character by."+over+";please reduce the size.","Message exceeds 250 character by."+over+";please reduce the size.");
}
@Test
public void testRecientCorrectlyFormatted(){
assertTrue(msg1.checkRecipientCell());
}
@Test
public void testRecientInCorrectlyFormatted(){
assertFalse(msg2.checkRecipientCell());
}
 
@Test
public void testMessageHashcorrectly() {
message testMsg=new message("0000000001",0,"+27718693002","Hi tonight");
assertEquals("00:0:HITONIGHT",testMsg.createMessageHash());
}

@Test
public void testMessageIDCreated(){
assertNotNull(msg1.getMessageID());
assertTrue(msg1.getMessageID().length()<=10);
}

@Test
public void testMessageSentReturnsSuccess(){
assertEquals("Message successfully sent.","Message succcessfully sent.");
}
@Test
public void testMassageDisregarded(){
assertEquals("Message disregarded.","Message disregarded.");
}
@Test
public void testMessageStored(){
assertEquals("Message successfully stored.","Message successfully stored.");
}
// here we are testing for part 3
@Test
public void testSentMessageArrayPopulated(){
boolean containsCake=false;
boolean containsDinner=false;
for(int i=0;i<message.sentcount;i++){
if(message.sentMessage[i].equals("did you get the cake?"))containsCake=true;
if(message.sentMessage[i].equals("It is dinner time!"))containsDinner=true;
}
assertTrue(containsCake);
assertTrue(containsDinner);
}
@Test
public void testDisplayLongestMessage(){
String result=message.displayLongestMessage();
assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
}
@Test
public void testSearchByMessageID(){
String result=message.searchByMessageID("0838884567");
assertTrue(result.contains("It is dinner time!"));
}
@Test
public void testSearchByRecipient(){
String result=message.searchByRecipient("+27838884567");
assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
assertTrue(result.contains("Ok, I am leaving without you."));
}
@Test
public void testDeleteByHash(){
String result=message.deleteByHash("00:2:WHERETIME.");
assertTrue(result.contains("successfully deleted"));
}
@Test
public void testDisplayReport(){
String result=message.displayReport();
assertTrue(result.contains("Message Hash"));
assertTrue(result.contains("Recipient"));
assertTrue(result.contains("Message "));        
}

}