/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat1;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 *
 * @author Student
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



class login {

    String storeUsername;
    String storePassword;
    String storeCellPhoneNumber;
    Scanner input = new Scanner(System.in);

    boolean checkUsername(String username) {
        if (username.contains("_") && username.length() == 5) {
            System.out.println("Username successfully captured");
            return true;
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length");
            System.out.println("Username incorrect, please try again");
            return false;
        }
    }

    boolean checkPasswordComplexity(String password) {
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (password.length() > 8) {
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUpperCase = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                } else if (!Character.isLetterOrDigit(c)) {
                    hasSpecial = true;
                }
            }

            if (hasUpperCase && hasDigit && hasSpecial) {
                System.out.println("Password successfully captured");
                return true;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains a capital letter, a number and a special character");
                return false;
            }
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains a capital letter, a number and a special character");
            System.out.println("Password incorrect, please try again");
            return false;
        }
    }

    boolean checkCellPhoneNumber(String cellPhoneNumber) {
          String regex="^\\+27[0-9]{9}$";
        if(cellPhoneNumber.matches(regex)) {
            System.out.println("Cell phone number successfully added");
            return true;
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code");
            return false;
        }
    }

    void registerUser() {
        System.out.println("\n======== REGISTER ==========");
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();
        System.out.print("Enter Cell Phone Number: ");
        String cellPhoneNumber = input.nextLine();

        storeUsername = username;
        storePassword = password;
        storeCellPhoneNumber = cellPhoneNumber;

        if (checkUsername(storeUsername) && checkPasswordComplexity(storePassword) && checkCellPhoneNumber(storeCellPhoneNumber)) {
            System.out.println("Successful registration!");
        } else {
            System.out.println("Failed registration!");
        }
    }
   boolean userlogin(String username,String password){
   return username.equals(storeUsername)&& password.equals(storePassword);
   }

    boolean userlogin() {
        System.out.println("\n======== LOGIN ==========");
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();

        if (username.equals(storeUsername) && password.equals(storePassword)) {
            System.out.println("Login successful! Welcome back, " + storeUsername + "!");
            return true;
        } else {
            System.out.println("Login failed. Username or password incorrect.");
            return false;
        }
    }
}
//here theres is part 2 and part3
class messageData {

String messageID;
int messageNumber;
String recipient;
String message;
String messageHash;
// i just add this is for part3 string flag is for part 3
String flag;

public messageData(String messageID, int messageNumber,String recipient,String message, String messageHash, String flag ){
this.messageID=messageID;
this.messageNumber=messageNumber;
this.recipient=recipient;
this.message=message;
this.messageHash=messageHash;
this.flag=flag;
}
}
// same here there will be a combination of part 2 and 3 like i will add flag and others
class message {

    private String messageID;
    private int numMessageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag;

    // this will be a part 3 array
    static String[]sentMessage = new String[100];
    static int sentcount=0;

    static String[]disregardedMessages= new String[100];
    static int disregardCount=0;
    static messageData[] storedMessages=new messageData[100];
    static int storedCount=0;
    static String[]messageHashes =new String[100];
    static String[]messageIDs= new String [100];
    static int totalMessages=0;


    public message(String messageID, int numMessageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.numMessageNumber = numMessageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }


     boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    boolean checkRecipientCell() {
        String regex="^\\+27[0-9]{9}$";
        return recipient.matches(regex);

        
    }

    String createMessageHash() {
        String idStart = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String first = words[0];
        String last = words[words.length - 1];
        String hash = idStart + ":" + numMessageNumber + ":" + first + last;
        return hash.toUpperCase();
    }

    String SentMessage(Scanner scanner) {
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1. Send");
        System.out.println("2. Store");
        System.out.println("3. Disregard");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
               this.flag="sent";
               addToArrays("sent");
               saveToJsonFile();
            case "2":
                this.flag="stored";
                addToArrays("stored");
               saveToJsonFile();
            case "3":
                this.flag="disregard";
                addToArrays("disregard");
                return"Message Disregarded";
            default:
                this.flag="disregard";
                addToArrays("disregard");
                return "Invalid choice. Message disregarded.";
        }
    }
   // this is continuation of part 3
    private void addToArrays(String flag){
        messageIDs[totalMessages]=messageID;
        messageHashes[totalMessages]=messageHash;
        totalMessages++;
        messageData data=new messageData(messageID,numMessageNumber,recipient,messageText,messageHash,flag);
        storedMessages[storedCount]=data;
        storedCount++;

        if(flag.equals("sent")){
        sentMessage[sentcount]=messageText;
        sentcount++;
        }else if(flag.equals("disregard")){
        disregardedMessages[disregardCount]= messageText;
        disregardCount++;
        }
    }
   static String displayAllStoredSenderRecipient(){
   if(storedCount ==0)return"no stored messages found.";
   StringBuilder sb= new StringBuilder();
   sb.append("\n=====Stored Messages- Sender & Recipient===\n");
   for(int i=0;i<storedCount;i++){
   sb.append("ID:").append(storedMessages[i].messageID)
      .append("Recipient:").append(storedMessages[i].recipient)
       .append("Flag:").append(storedMessages[i].flag).append("\n");
   }
   return sb.toString();
   }
     static String displayLongestMessage(){
     if(storedCount ==0)return "no stored messages found.";
     messageData longest =storedMessages[0];
     for(int i=1;i<storedCount;i++){
     if(storedMessages[i].message.length()>longest.message.length()){
       longest=storedMessages[i];
         }
     }
     return"\nLongest message:\n Recipient"+longest.recipient+
             "\n message:"+longest.message+
             "\n Length"+longest.message.length()+"characters:";
     }
     static String searchByMessageID(String id){
     for(int i=0;i<storedCount;i++){
     if(storedMessages[i].messageID.equalsIgnoreCase(id)){
      return"\nMessage Found:\n Recipient:"+storedMessages[i].recipient+
              "\n Message:"+storedMessages[i].message;
     }
     }
     return"Message ID not found.";
     }
     static String searchByRecipient(String recipient){
    StringBuilder sb= new StringBuilder();
    boolean found =false;
    for(int i=0;i<storedCount;i++){
    if (storedMessages[i].recipient.equalsIgnoreCase(recipient)){
    sb.append(" - ").append(storedMessages[i].message).append("\n");
    found =true;
    }
    }
    if (!found)return "No messages found for recipient:"+recipient;
    return"\nMessages for"+recipient+":\n"+sb.toString();
     }
    static String deleteByHash(String hash){
    for(int i=0;i<storedCount;i++){
    if(storedMessages[i].messageHash.equalsIgnoreCase(hash)){
    String deletedMsg=storedMessages[i].message;
    for(int j=i;j<storedCount-1;j++){
    storedMessages[j]=storedMessages[j+1];
    }
    storedMessages[storedCount-1]=null;
    storedCount--;
    return "Message:\"" +deletedMsg+"\"successsfully deleted.";
      }
    }
    return "Message hash not found.";
    }
    static String displayReport(){
    if(storedCount ==0)return "no stored messages to report.";
    StringBuilder sb= new StringBuilder();
    sb.append("\n======FULL Message Report======\n");
    for(int i=0;i<storedCount;i++){
    sb.append("--------------------\n");
    sb.append("Message Hash:").append(storedMessages[i].messageHash).append("\n");
    sb.append("Recipient:").append(storedMessages[i].recipient).append("\n");       
    sb.append("Message:").append(storedMessages[i].message).append("\n");        
    sb.append("Flag:").append(storedMessages[i].flag).append("\n");        
            
    }
    sb.append("=====================\n");
    return sb.toString();
    }
    
    
    
    static String printMessages() {
        if (totalMessages == 0) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== All Sent Messages =====\n");
        for (int i = 0; i < totalMessages; i++) {
            sb.append(storedMessages[i]).append("\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessage() {
        return totalMessages;
    }

   
    public void saveToJsonFile(){
    Gson gson=new GsonBuilder().setPrettyPrinting().create();

    messageData data=new messageData(messageID,numMessageNumber,recipient,messageText,messageHash,flag );
    
    try (FileWriter writer=new FileWriter("messagees.json",true)){
    gson.toJson(data,writer);
        System.out.println("Message saved to message.json");
    }catch(IOException e){
        System.out.println("Error saving message:"+ e.getMessage());
            }

    }


    String getMessageID()   { return messageID; }
    String getRecipient()   { return recipient; }
    String getMessage()     { return messageText; }
    String getMessageHash() { return messageHash; }
}
public class _QuickChat {
static Scanner scanner = new Scanner(System.in);
    static int numMessageSent = 0;
    static int maxMessages = 0;
    static boolean loggedIn = false;

public static void main(String[] args) {
  

        login loginApp = new login();
        int choice;

        do {
            System.out.println("\n====== MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Menu Option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginApp.registerUser();
                    break;
                case 2:

                    loggedIn = loginApp.userlogin();
                    if (loggedIn) {
                        runQuickChat();
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.Please enter 1,2,3");
            }
        } while (choice != 3);

        scanner.close();
    }


    static void runQuickChat() {
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages would you like to send? ");
        maxMessages = Integer.parseInt(scanner.nextLine().trim());

        boolean running = true;
        while (running) {
            System.out.println("\n---- Menu -----");
            System.out.println("1. Send Message");
            System.out.println("2. Show recent sent messages");
            System.out.println("3. stored Messages");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");
            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    System.out.println(message.printMessages());
                    break;
                case "3":
                    storedMessagesMenu();
                    break;
                    case"4":
                     running=false;
                        System.out.println("Total messages:"+message.returnTotalMessage());
                        System.out.println("GoodBye");
                        break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // this part here is for part 3 the stored messages sub menu
    static void storedMessagesMenu(){
    boolean back=false;
    while(!back){
        System.out.println("\n========Stored Message Menu");
        System.out.println("A. Display sender and recipient of all stored messages");
        System.out.println("B. Display the longer stored message");
        System.out.println("C. Search for a message by ID");
        System.out.println("D. Search all messages for a puticular recipient ");
        System.out.println("E. Delete a message using the message hash ");
        System.out.println("F. Display full report");
        System.out.println("G.  back to main menu ");
        System.out.println("Choose:");
        String Choice=scanner.nextLine().trim().toUpperCase();
        
        switch(Choice){
        case"A":
            System.out.println(message.displayAllStoredSenderRecipient());
            break;
        case"B":
            System.out.println(message.displayLongestMessage());
            break;
        case"C":
            System.out.println("Enter Message ID to search:");
            String id=scanner.nextLine().trim();
            System.out.println(message.searchByMessageID(id));
            break;
        case"D":
             System.out.println("Enter rcipient cell number:");
             String recipient=scanner.nextLine().trim();
             System.out.println(message.searchByRecipient(recipient));
             break;
        case"E":
            System.out.println("Enter message hash to delete:");
            String hash=scanner.nextLine().trim();
            System.out.println(message.deleteByHash(hash));
            break;
        case"F":
            System.out.println(message.displayReport());
            break;
        case"G":
            back=true;
            break;
        default:
            System.out.println("Invalid option.");
        }
    }
    
    }
    static void sendMessages() {
        if (numMessageSent >= maxMessages) {
            System.out.println("Message limit of " + maxMessages + " reached.");
            return;
        }

        int remaining = maxMessages - numMessageSent;
        System.out.println("You can send " + remaining + " more message(s).");

        for (int i = 0; i < remaining; i++) {
            System.out.println("\n--- Message " + (numMessageSent + 1) + " ---");

            String recipient = "";
            while (true) {
                System.out.print("Enter recipient cell number (e.g. +27xxxxxxxx, max 10 chars): ");
                recipient = scanner.nextLine().trim();
                String regex="^\\+27[0-9]{9}$";
                if (recipient.matches(regex))break;
                System.out.println("Invalid. Must start with + and be max 10 characters.");
            }

            String messageText = "";
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = scanner.nextLine();
                if (messageText.length() <= 250) break;
                System.out.println("Please enter a message of less than 250 characters.");
            }

            String messageID = generateMessageID();
            numMessageSent++;

            message msg = new message(messageID, numMessageSent, recipient, messageText);


            if (!msg.checkMessageID()) {
                System.out.println("Error: Message ID invalid.");
                numMessageSent--;
                continue;
            }



            System.out.println("Message Hash: " + msg.getMessageHash());

            String result = msg.SentMessage(scanner);
            System.out.println(result);

            if (numMessageSent >= maxMessages) {
                System.out.println("\nMessage limit reached.");

                break;
            }
        }


        System.out.println(message.printMessages());
    }

    static String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {            id.append(rand.nextInt(10));
        }
        return id.toString();
    }
}