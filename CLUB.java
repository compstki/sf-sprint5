import javax.swing.JOptionPane;
import java.io.*;               // for general file handling
public class CLUB
{
    // array of MEMBER objects
    private MEMBER memberList[];
    // number of members calculated after reading file
    int noOfMembers;

    // CLASSes to open, create, read/write, close files
    FILEREADCSV bmiFile;        // to read file from storage
    FILEWRITECSV resultFile;    // to write data to storage

    public CLUB()  throws IOException
    {
        // create file handler objects
        bmiFile = new FILEREADCSV();
        resultFile = new FILEWRITECSV() ;
    }

    // top level algorithm
    public void processMembers()  throws IOException
    {
        setUpMemberList();
        displayMembers();
        countOKbmi();
    }

    private void setUpMemberList() throws IOException
    {
        // read file, fetch data as String array containing the rows
        String[] dataRows = bmiFile.readCSVtable();
        // calculate the number of member rows, skip headings
        noOfMembers = dataRows.length - 1;

        // prepare array for members
        memberList = new MEMBER[noOfMembers];
        // create member objects and copy data from source
        for  (int i = 0; i < noOfMembers; i++) {
            memberList[i] = new MEMBER();
            // adjust to skip headings
            memberList[i].readMemberDetails(dataRows[i+1]);
        }
    }

    public void displayMembers() {
        for  (int i = 0; i < noOfMembers; i++) {
            memberList[i].displayDetails();
        }
    }

    public void countOKbmi() throws IOException
    {
        // *prepare a String to write data to disc
        String fileContent = "";

        // start the count
        int count = 0;
        // loop for each item : member
        for (int i = 0; i < noOfMembers; i++)
        {
            // decide if current item: member matches target: bmi
            if ((memberList[i].getBMI() > 18.5) && (memberList[i].getBMI() < 25) )
            {
                // add 1 to count: for OK bmi
                count = count +1;
                
                // *display the details for the member
                memberList[i].displayDetails();

                // *use new line to separate rows in csv file, after 1st line
                if (i>0) {
                    fileContent = fileContent.concat("\n");
                }
                // *join on next line of data for writing to file
                fileContent = fileContent.concat(memberList[i].writeDetails());
            }
        }

        // display the final count: bmi
        System.out.println("Total for OK BMI is : " + count);

        // *send for writing to file
        resultFile.writeCSVtable(fileContent);
    }

    public static void main(String[] args)  throws IOException
    {
        CLUB myClub = new CLUB();
        myClub.processMembers();
    }
}
