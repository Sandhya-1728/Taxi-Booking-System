import java.util.*;
public class Booking{
    public static List<Taxi> createTaxis(int n){
        List<Taxi> taxis = new ArrayList<Taxi>();
        for(int i=1;i<=n;i++){
            Taxi t=new Taxi();
            taxis.add(t);
        }
        return taxis;
    }

    public static List<Taxi> getFreeTaxis(List<Taxi> taxis,int pickupTime,char pickup){
        List<Taxi> availableTaxis = new ArrayList<Taxi>();
        for(Taxi i:taxis){
            if(i.booked==false){
                if(i.freeTime<=pickupTime && (Math.abs((i.currentSpot - '0') - (pickup - '0')) <= pickupTime - i.freeTime)){
                        availableTaxis.add(i);
                    
                }
            }
        }
        return availableTaxis;
    }

    public static void bookTaxi(int customerId,char pickup,char drop,int pickupTime,List<Taxi> availableTaxis){
        int min=1000;
        int distanceBetweenPickupAndDrop=0;
        int earning=0;
        int nextFreeTime=0;
        char nextSpot='Z';
        Taxi bookedTaxi=null;
        String tripDetails="";
        for(Taxi i:availableTaxis){
            int distanceBetweenCustomerAndTaxi=Math.abs((i.currentSpot-'0')-(pickup-'0'))*15;
            if(distanceBetweenCustomerAndTaxi<min){
                bookedTaxi=i;
                distanceBetweenPickupAndDrop=Math.abs((pickup-'0')-(drop-'0'))*15;
                earning=(distanceBetweenPickupAndDrop-5) * 10 + 100;
                int dropTime=pickupTime + distanceBetweenPickupAndDrop/15;
                nextFreeTime=dropTime;
                nextSpot =drop;

                tripDetails="    "+customerId+"                    "+bookedTaxi.id+"                "+pickup+"        "+drop+"             "+pickupTime+"               "+nextFreeTime+"           "+earning;
                min=distanceBetweenCustomerAndTaxi;
            }
            
            
        }

        bookedTaxi.setDetails(true,nextSpot,nextFreeTime,bookedTaxi.totalEarnings+earning,tripDetails);
        System.out.println("Taxi "+bookedTaxi.id+" is booked");

    }

    public static void main(String[] args){

        List<Taxi> taxis =createTaxis(5);
        Scanner sc=new Scanner(System.in);
        int id=1;

        while(true){
            System.out.println("!!!  Welcome to the Taxi Booking System  !!!");
            System.out.println("1. Book a Taxi");
            System.out.println("2. Print Taxi Details");
            System.out.println("3. Print Booking Details");
            System.out.println("4. Exit");
            System.out.println("Enter your choice");
            int choice=sc.nextInt();
            switch(choice){
                case 1:
                {
                    int customerId =id;
                    System.out.println("Enter the pickup location");
                    char pickup=sc.next().charAt(0);
                    System.out.println("Enter the drop location");
                    char drop=sc.next().charAt(0);
                    System.out.println("Enter the pickup time");
                    int pickupTime=sc.nextInt();


                    if(pickup<'A' || drop>'H' || pickup>'H' || drop<'A'){
                        System.out.println("!Invalid location! Valid locations are A,B,C,D,E,F,G,H");
                        return;
                    }

                    List<Taxi> availableTaxis = getFreeTaxis(taxis,pickupTime,pickup);

                    if(availableTaxis.size()==0){
                        System.out.println("No taxi available");
                        return;
                    }

                    Collections.sort(availableTaxis,(a,b)->a.totalEarnings-b.totalEarnings);

                    bookTaxi(id,pickup,drop,pickupTime,availableTaxis);
                    id++;
                    break;
                }

                case 2:
                {
                    for(Taxi i:taxis){
                        i.printTaxiDetails();
                    }
                    break;
                }

                case 3:
                {
                    for(Taxi i:taxis){
                        i.printDetails();
                    }
                    break;
                }

                case 4:
                {
                    System.exit(0);
                    break;
                }

                default:
                {
                    System.out.println("Invalid choice");
                    break;
                }
                /*case 1:
                    System.out.println("Enter the pickup location");
                    char pickup=sc.next().charAt(0);
                    System.out.println("Enter the drop location");
                    char drop=sc.next().charAt(0);
                    System.out.println("Enter the pickup time");
                    int pickupTime=sc.nextInt();
                    System.out.println("Enter the customer id");
                    int customerId=sc.nextInt();
                    int min=1000000;
                    int minTaxi=0;
                    for(Taxi i:taxis){
                        if(i.booked==false){
                            if(i.freeTime<=pickupTime){
                                if(i.currentSpot==pickup){
                                    if(i.totalEarnings<min){
                                        min=i.totalEarnings;
                                        minTaxi=i.id;
                                    }
                                }
                            }
                        }
                    }
                    if(minTaxi==0){
                        System.out.println("No taxi available");
                    }
                    else{
                        System.out.println("Taxi "+minTaxi+" is booked");
                        int dropTime=pickupTime+Math.abs(pickup-drop);
                        int fare=10*Math.abs(pickup-drop);
                        taxis.get(minTaxi-1).setDetails(true,drop,dropTime,taxis.get(minTaxi-1).totalEarnings+fare,id+" "+minTaxi+" "+customerId+" "+pickup+" "+drop+" "+pickupTime+" "+dropTime+" "+fare);
                        id=id+1;
                    }
                    break;
                case 2:
                    for(Taxi i:taxis){
                        i.printTaxiDetails();
                    }
                    break;
                case 3:
                    for(Taxi i:taxis){
                        i.printDetails();
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;*/
            }
        }
    }
}
