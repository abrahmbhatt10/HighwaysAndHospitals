import java.util.*;


/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Agastya Brahmbhatt
 *
 */

public class HighwaysAndHospitals {
    /*
            Sorts by value so that most connected cities
            are at the beginning of the hashmap.

            I got the sorting code from this link:
            https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
         */
    // function to sort hashmap by values
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    /*
     *  This function returns the minimum cost to provide
     *  hospital access for all citizens in the county.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        long minCost, myCost = 0;
        Map<Integer, Integer> hCities = new HashMap<Integer, Integer>();
        Map<Integer, Integer> bHighways = new HashMap<Integer, Integer>();
        int mostConnectedCity;
        int connectedCityNum;
        Map<Integer, Integer> numConnectedCities = new HashMap<Integer, Integer>();
        /*
            If the cost to build a highway is less than simply building two hospitals in two unconnected cities
        then it makes sense to just build hospitals in all the cities and no highways.
         */
        if(n == 1)
        {
            System.out.println("n is 1, Min cost is "+ (hospitalCost*n));
            return hospitalCost;
        }
        if(cities == null || cities.length == 0)
        {
            return (n * hospitalCost);
        }
        if(n == 2)
        {
            //System.out.println("n is 2 times hospital less Min cost is "+ (hospitalCost+highwayCost));
            if((2*hospitalCost) < (hospitalCost+highwayCost))
            {
                return (2 * hospitalCost);

            }
            return hospitalCost+highwayCost;
        }
        /*
            Calculates the number of connections per city
         */
        int key = 0;
        for(int i = 0; i < cities.length ; i++)
        {
            key = cities[i][0];
            numConnectedCities.put(key, numConnectedCities.containsKey(key) ? numConnectedCities.get(key) + 1 : 1);
            key = cities[i][1];
            numConnectedCities.put(key, numConnectedCities.containsKey(key) ? numConnectedCities.get(key) + 1 : 1);
        }
        // Sorts the list
        Map<Integer, Integer> sortedClustersByCity = sortByValue((HashMap<Integer, Integer>) numConnectedCities);
        /*
            I google searched how to iterate through a hashmap and realized I need
            to change hashmap to an arraylist:
            https://stackoverflow.com/questions/24943302/convert-an-entryset-to-an-array
         */
        Map.Entry<Integer, Integer>[] sortedList = new Map.Entry [sortedClustersByCity.size()];
        sortedClustersByCity.entrySet().toArray(sortedList);
        minCost = Long.MAX_VALUE;
        /*
            I am iterating through the most connected cities.
         */
        for(int i = 0; i < sortedList.length; i++){
            mostConnectedCity = sortedList[sortedList.length-1-i].getKey();
            connectedCityNum = sortedList[sortedList.length-1-i].getValue();
            /*
                If there is no connection, then break out.
             */
            if(mostConnectedCity == 0 || connectedCityNum == 0)
            {
                break;
            }
            /*
                Check if the most connected city is already in the built-highways list.
             */
            if(bHighways.containsKey(mostConnectedCity)){
                /*
                    For example, this is a city connected to two clusters.
                    It has access from a hospital from a previous cluster.
                    A decision needs to be made based on cost whether the second cluster connected nodes should be connected
                    Through highway.
                 */
                if(getSubCost(1, hospitalCost, connectedCityNum, highwayCost) == 0) {
                    //only add highways if cheaper than hospital in each city
                    addConnectedCities(hCities, bHighways, cities, mostConnectedCity);
                }
            }
            else{
                /*
                    This is a new city that is not connected by highways.
                    Check whether it has a hospital of its own.
                 */
                if(!(hCities.containsKey(mostConnectedCity)))
                {
                    //Cheaper to build hospital at root node and all highways connected to it
                    if(getSubCost(1, hospitalCost, connectedCityNum, highwayCost) == 0) {
                        /*
                            Adding this city to build a hospital with all connected highways.
                         */
                        hCities.put(mostConnectedCity, connectedCityNum);
                        if (!(bHighways.containsKey(mostConnectedCity))) {
                            addConnectedCities(hCities, bHighways, cities, mostConnectedCity);
                        }
                    }
                    else
                    {
                        //Cheaper to build hospitals, but just entering single hospital node because it's expensive to build highways.
                        hCities.put(mostConnectedCity, 0);
                    }
                }
            }
            myCost = getMyCost(n, hCities.size(), hospitalCost, bHighways.size(),highwayCost);
            if(myCost < 0)
                break;
            if(myCost < minCost && myCost > 0)
            {
                minCost = myCost;
            }
        }
        myCost = getMyCost(n, hCities.size(), hospitalCost, bHighways.size(),highwayCost);
        if(myCost < minCost && myCost > 0)
        {
            minCost = myCost;
        }
        //String citiesStr = "";
        //for(int i =0; i < cities.length; i++)
        //{
          //  citiesStr += "-"+cities[i][0]+"_"+cities[i][1];
        //}
        //System.out.println("Total Cities "+n+ " Possible Highways "+citiesStr);
        System.out.println("Total Cities "+n);
        System.out.println("Hospital Cost " + hospitalCost+ " Highways Cost "+ highwayCost);
        System.out.println("Hospitals in "+hCities.toString());
        System.out.println("Highways built "+bHighways.toString());
        System.out.println("Min cost is "+ minCost);
        return minCost;
    }

    public static long getMyCost(int n, int numCities, int hospitalCost, int numHighways, int highwayCost)
    {
        long mycost = 0;
        boolean errflag = false;
        long n1, n2, n3, n4, n5, n6, n7 = 0;
        /*
            I googled searched how to avoid long overflow in java
            https://mkyong.com/java8/java-8-math-exact-examples/
         */
        try {
            n1 = Math.multiplyExact((int)hospitalCost, numCities);
            n2 = Math.multiplyExact((int)highwayCost ,numHighways);
            n3 = Math.addExact(numHighways,numCities);
            n4 = Math.subtractExact(n, n3);
            n5 = Math.multiplyExact(n4,hospitalCost);
            n6 = Math.addExact(n1,n2);
            n7 = Math.addExact(n6, n5);
        }
        catch (ArithmeticException e)
        {
            errflag = true;
            mycost = 0;
        }
        if(!errflag)
        {
            mycost = n7;
        }
        return mycost;
    }
    /* return 0 if hospital on root and all highways connected
       return 1 if all hospitals for that root node and no connections are added
     */
    public static long getSubCost(int numCities, int hospitalCost, int numHighways, int highwayCost)
    {
        long mycostHospital = 0, mycostHighways=0 ;
        boolean errflag = false;
        long n1, n2, n3, n4, n5, n6, n7 = 0;
        try {
            n1 = Math.multiplyExact((int)hospitalCost, numCities);
            n2 = Math.multiplyExact((int)highwayCost ,numHighways);
            mycostHighways = Math.addExact(n1,n2);
            mycostHospital = Math.multiplyExact(hospitalCost, (numCities+numHighways));
        }
        catch (ArithmeticException e)
        {
            errflag = true;
        }
        if(!errflag && mycostHighways < mycostHospital)
        {
            return 0;
        }
        return 1;
    }

    public static void addConnectedCities(Map<Integer, Integer> hCities, Map<Integer, Integer> bHighways, int cities[][], int rootCityNumber){
        for(int i = 0; i < cities.length; i++){
            if(cities[i][0] == rootCityNumber)
            {
                if((!bHighways.containsKey(cities[i][1])) && (!hCities.containsKey(cities[i][1]))){
                    bHighways.put(cities[i][1], rootCityNumber);
                }
            }
            if(cities[i][1] == rootCityNumber)
            {
                if((!bHighways.containsKey(cities[i][0])) && (!hCities.containsKey(cities[i][0]))){
                    bHighways.put(cities[i][0], rootCityNumber);
                }
            }
        }
    }

}
