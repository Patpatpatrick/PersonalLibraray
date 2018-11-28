//package test;
//import control.backendcenter.PublicationOperationCenter;
//import exception.borrowexception.NoSpareAvailable;
//import exception.publicationexception.NonExistException;
//import model.publication.PublicationItems;
//import model.Borrower.BorrowerList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import control.frontcenter.LibraryFrontCenter;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//
//public class LibraryTest {
//    private PublicationOperationCenter testOperationCenter;
//    private LibraryFrontCenter testLib;
//
//    @BeforeEach
//    public void Setup() throws IOException {
//        testOperationCenter=new PublicationOperationCenter();
//        testLib=new LibraryFrontCenter();
//    }
//
//
//    @Test
//    public void testDisplaySpecificBookname(){
//        try {
//            testOperationCenter.displayByName("cpsc210");
//        } catch (NonExistException e) {
//            fail("Should not catch this!");
//        }
//        try {
//            testOperationCenter.displayByName("math300");
//        } catch (NonExistException e) {
//            fail("Should not catch this!");
//        }
//        try {
//            testOperationCenter.displayByName("Gone with the wind");
//            fail("should not be here");
//        } catch (NonExistException e) {
//            System.out.println("We should catch this.");
//        }
//    }
//
//
//
//    @Test
//    public void testBorrowBookNoSpareAvailable(){
//        boolean status=false;
//        try {
//            status=testOperationCenter.borrowOrReserveItems("cpsc210", BorrowerList.getBorrowerArrayList().get(0));
//        } catch (NonExistException e) {
//            fail("Should not catch this!");
//        } catch (NoSpareAvailable noSpareAvailable) {
//            fail("Should not catch this!");
//        }
//        assertTrue(status);
//        try {
//            testOperationCenter.borrowOrReserveItems("cpsc210", BorrowerList.getBorrowerArrayList().get(1));
//        } catch (NonExistException e) {
//            fail("Should not catch this!");
//        } catch (NoSpareAvailable noSpareAvailable) {
//            fail("Should not catch this!");
//        }
//        try {
//            testOperationCenter.borrowOrReserveItems("cpsc210", BorrowerList.getBorrowerArrayList().get(2));
//            fail("should not come here");
//        } catch (NonExistException e) {
//            fail("should not come here");
//        } catch (NoSpareAvailable noSpareAvailable) {
//            System.out.println("we should catch this.");
//        }
//    }
//
//
//}
