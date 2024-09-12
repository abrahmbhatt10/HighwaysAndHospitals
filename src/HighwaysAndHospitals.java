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
        Map<Integer, Integer> clustersByCity = new HashMap<Integer,Integer>();
        int maxValue = n+1;
        int mostConnectedCity;
        int connectedCityNum;
        int [] numConnectedCities = new int[n+1];
        /*
            If the cost to build a highway is less than simply building two hospitals in two unconnected cities
        then it makes sense to just build hospitals in all of the cities and no highways.
         */
        if((2 * hospitalCost) < highwayCost){
            System.out.println("2 times hospital less Min cost is "+ (hospitalCost*n));
            return (hospitalCost * n);
        }
        if(n == 1)
        {
            System.out.println("n is 1Min cost is "+ (hospitalCost*n));
            return hospitalCost;
        }
        if(n == 2)
        {
            if(cities.length == 0)
            {
                return (2*hospitalCost);
            }
            //System.out.println("n is 2 times hospital less Min cost is "+ (hospitalCost+highwayCost));
            return hospitalCost+highwayCost;
        }
        /*
            Calculates the number of connections per city
         */
        for(int i = 1; i < n+1; i++)
        {
            numConnectedCities[i] = getNumOfConnectedCities(i,cities);
        }
        /*
            Input the number of connections per city in a hashmap.
         */
        for(int i = 0; i < n+1; i++){
            clustersByCity.put(i, numConnectedCities[i]);
        }
        // Sorts the list
        Map<Integer, Integer> sortedClustersByCity = sortByValue((HashMap<Integer, Integer>) clustersByCity);
        //System.out.println("Unsorted " + clustersByCity.toString());
        //System.out.println("Sorted " + sortedClustersByCity.toString());
        Map.Entry<Integer, Integer>[] sortedList = new Map.Entry [sortedClustersByCity.size()];
        sortedClustersByCity.entrySet().toArray(sortedList);
        minCost = Long.MAX_VALUE;
        for(int i = 0; i < n+1; i++){
            mostConnectedCity = sortedList[n-i].getKey();
            connectedCityNum = sortedList[n-i].getValue();
            if(mostConnectedCity == 0 || connectedCityNum == 0)
            {
                break;
            }
            if(bHighways.containsKey(mostConnectedCity)){
                addConnectedCities(hCities, bHighways, cities, mostConnectedCity);
            }
            else{
                if(!(hCities.containsKey(mostConnectedCity)))
                {
                    hCities.put(mostConnectedCity, connectedCityNum);
                    if(!(bHighways.containsKey(mostConnectedCity))){
                        addConnectedCities(hCities, bHighways, cities, mostConnectedCity);
                    }
                }
            }
            boolean errflag = false;
            long n1, n2, n3, n4, n5, n6, n7 = 0;
            try {
                n1 = Math.multiplyExact((int)hospitalCost,(int)hCities.size());
                n2 = Math.multiplyExact((int)highwayCost ,(int)bHighways.size());
                n3 = Math.addExact((int)hCities.size(),(int)bHighways.size());
                n4 = Math.subtractExact(n, n3);
                n5 = Math.multiplyExact(n4,hospitalCost);
                n6 = Math.addExact(n1,n2);
                n7 = Math.addExact(n6, n5);
            }
            catch (ArithmeticException e)
            {
                errflag = true;
            }
            if(!errflag)
            {
                myCost = n7;
            }
            if(myCost < 0)
                break;
            if(myCost < minCost && myCost > 0)
            {
                minCost = myCost;
            }
        }
        //String citiesStr = "";
        //for(int i =0; i < cities.length; i++)
        //{
          //  citiesStr += "-"+cities[i][0]+"_"+cities[i][1];
        //}
        //System.out.println("Total Cities "+n+ " Possible Highways "+citiesStr);
        System.out.println("Total Cities "+n);
        System.out.println("Hospital Cost " + hospitalCost+ " Highways Cost "+ highwayCost);
        //System.out.println("Hospitals in "+hCities.toString());
        //System.out.println("Highways built "+bHighways.toString());
        System.out.println("Min cost is "+ minCost);
        return minCost;
    }

    public static int getNumOfConnectedCities(int cityNumber, int cities[][]){
        int answer = 0;
        for(int i = 0; i < cities.length; i++){
            if((cities[i][0] == cityNumber) || (cities[i][1] == cityNumber)){
                answer++;
            }
        }
        return answer;
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

    /*
    public static int getMostConnectedCity(int maxValue, int numConnectedCities[]){
        int answer = 0;
        int mostConnected = 0;
        int mostConnectedCity = 0;
        for(int i = 1; i < numConnectedCities.length; i++)
        {
            answer = numConnectedCities[i];
            if((answer != -1) && (mostConnected < answer) && (answer < maxValue)){
                mostConnected = answer;
                mostConnectedCity = i;
            }
        }
        numConnectedCities[mostConnectedCity] = -1;
        return mostConnectedCity;
    }*/
}
