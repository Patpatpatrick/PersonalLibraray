//package ui;
//
//import control.frontcenter.LibraryFrontCenter;
//import model.Borrower.BorrowerList;
//
//import java.io.IOException;
//
//public class Runlib {
//    public Runlib() throws IOException {
//        System.out.println("Welcome to my library");
//        LibraryFrontCenter.getInstance();
//        System.out.println(BorrowerList.getBorrowerHashMap());
////        Scanner userInputScanner = new Scanner(System.in);
////        int k=1;
////        do {
////            printInfo();
////            library.setOperator(userInputScanner.nextInt());
////            if (library.getOperator()==1) {
////                do{
////                    System.out.println("1-book;2-cd");
////                    try{
////                        library.setOperator(userInputScanner.nextInt());
////                    }catch (InputMismatchException e){
////                        System.out.println("You must input a number 1 or 2");
////                        break;
////                    }
////                    if(library.getOperator()==1) {
////                        library.addBookInteraction();
////                        break;
////                    }
////                    else if(library.getOperator()==2){
////                        library.addCDInteraction();
////                        break;
////                    }
////                    else
////                        System.out.println("1 or 2 please");
////                }while (true);
////            }
////            else if(library.getOperator()==2) {
////                System.out.println("1-book;2-cd");
////
////                try{
////                    library.setOperator(userInputScanner.nextInt());
////                }catch (InputMismatchException e){
////                    System.out.println("You must input a number 1 or 2");
////                }
////                if(library.getOperator()==1){
////                    try {
////                        library.borrowBookInteraction();
////                    } catch (NonExistException e) {
////                        System.out.println("No such book.");
////                        continue;
////                    } catch (NoSpareAvailable noSpareAvailable) {
////                        System.out.println("No available book.");
////                        continue;
////                    } catch (ExceedMaxLoanCreditException e) {
////                        System.out.println("Exceed max credit");
////                        continue;
////                    } catch (NoDuplicateLoanException e) {
////                        System.out.println("You cannot make duplicate loan");
////                        continue;
////                    } catch (NegRemainingException e) {
////                        System.out.println("You cannot make remaining negative");
////                        continue;
////                    } catch (BorrowUnsuccessfulException e) {
////                        System.out.println("Borrow unsuccessful");
////                        continue;
////                    }
////                }
////                else if(library.getOperator()==2){
//////                    try {
//////                            library.borrowCDInteraction();
//////                        } catch (NonExistException e) {
//////                            System.out.println("No such cd.");
//////                            continue;
//////                        } catch (NoSpareAvailable noSpareAvailable) {
//////                            System.out.println("No spare available");
//////                            continue;
//////                        } catch (ExceedMaxLoanCreditException e) {
//////                            System.out.println("Exceed max credit");
//////                            continue;
//////                        } catch (NoDuplicateLoanException e) {
//////                            System.out.println("You cannot make duplicate loan");
//////                            continue;
//////                        } catch (NegRemainingException e) {
//////                            System.out.println("You cannot make remaining negative");
//////                            continue;
//////                        }
////                    }
//////                    else
//////                        System.out.println("1 or 2 please");
////
////            }
////            else if (library.getOperator()==3){
////
////                System.out.println("1-book;2-cd");
////                library.setOperator(userInputScanner.nextInt());
////                if(library.getOperator()==1)
////                    library.DisplayBooksInteraction();
////                else
////                    library.DisplayCDsInteraction();
////            }
////            else if (library.getOperator()==4){
////                try {
////                    library.returnBooksInteraction();
////                } catch (NonExistException e) {
////                    System.out.println("Wrong username");
////                    continue;
////                } catch (RemainingExceedMultiException e) {
////                    System.out.println("Remaining can not exceed multiplicity");
////                    continue;
////                }
////            }
////            else if (library.getOperator()==5){
////                library.booklost();
////            }
////            else if(library.getOperator()==6){
////                library.getfine();
////                library.payfine();
////            }
////            else if(library.getOperator()==7){
////                library.addBorrowerInput();
////            }
////            else if(library.getOperator()==0){
////                break;
////            }
////            else{
////                System.out.println("invalid input");
////            }
////            System.out.println("input 0 if you want to exit the application, or any other number to proceed");
////        }while(userInputScanner.nextInt()!=0);
////        System.out.println("exit application");
//
//    }
//    public static void main(String[] args) throws IOException {
//        System.out.println("hello!");
//        System.out.println("Please enter what you would like to do: ");
//        Runlib a=new Runlib();
//    }
//
//    public void printInfo(){
//        System.out.println("Please enter what you would like to do: ");
//        System.out.println("input 1 to add a book/cd");
//        System.out.println("      2 to loan a book/cd");
//        System.out.println("      3 to see some or all the books/CDs");
//        System.out.println("      4 to return a book/cd");
//        System.out.println("      5 to report a lost book/cd");
//        System.out.println("      6 to pay fine");
//        System.out.println("      7 to sign up as a borrower");
//        System.out.println("      0 to exit");
//    }
//}
