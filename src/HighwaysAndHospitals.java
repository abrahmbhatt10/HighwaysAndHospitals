import java.util.HashMap;
import java.util.Map;


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
     * This function returns the minimum cost to provide
     *  hospital access for all citizens in the county.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        long minCost = 0;
        Map<Integer, Integer> hCities = new HashMap<Integer, Integer>();
        Map<Integer, Integer> connectedCities = new HashMap<Integer, Integer>();
        int maxValue = n+1;
        int mostConnectedCity;
        int connectedCityNum;
        int [] numConnectedCities = new int[n+1];
        for(int i = 1; i < n+1; i++)
        {
            numConnectedCities[i] = getNumOfConnectedCities(i,cities);
        }
        for(int i = 0; i < cities.length; i++){
            mostConnectedCity = getMostConnectedCity(maxValue, numConnectedCities);
            connectedCityNum = getNumOfConnectedCities(mostConnectedCity, cities);
            System.out.println("Most connected "+mostConnectedCity+" with "+connectedCityNum);
            if(mostConnectedCity == 0 || connectedCityNum == 0)
            {
                break;
            }
            if(connectedCities.containsKey(mostConnectedCity)){
                addConnectedCities(hCities, connectedCities, cities, mostConnectedCity);
            }
            else{
                if(!(hCities.containsKey(mostConnectedCity)))
                {
                    hCities.put(mostConnectedCity, connectedCityNum);
                    if(!(connectedCities.containsKey(mostConnectedCity))){
                        addConnectedCities(hCities, connectedCities, cities, mostConnectedCity);
                    }
                }
            }
            System.out.println("Hospitals "+ hCities.toString());
            System.out.println("Highways "+ connectedCities.toString());
        }
        for(int i = 1; i < n+1; i++){
            if(!hCities.containsKey(i) && !connectedCities.containsKey(i)){
                hCities.put(i, 0);
            }
        }
        System.out.println("Hospitals "+ hCities.toString());
        System.out.println("Hightways "+ connectedCities.toString());
        System.out.println("NumCost "+hospitalCost+" "+highwayCost);
        String citiesStr = "";
        for(int i =0; i < cities.length; i++)
        {
            citiesStr += "-"+cities[i][0]+"_"+cities[i][1];
        }
        System.out.println(citiesStr);
        int numHospitals = hCities.size();
        int numHighways = connectedCities.size();
        minCost = (numHospitals * hospitalCost) + (numHighways * highwayCost);
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

    public static void addConnectedCities(Map<Integer, Integer> hCities, Map<Integer, Integer> connectedCities, int cities[][], int rootCityNumber){
        System.out.println("Adding connected for "+rootCityNumber);
        for(int i = 0; i < cities.length; i++){
            System.out.println("*"+cities[i][0]+"**"+cities[i][1]);
            if((cities[i][0] == rootCityNumber) && (!connectedCities.containsKey(cities[i][1])) && (!hCities.containsKey(cities[i][1]))){
                connectedCities.put(cities[i][1], rootCityNumber);
            }
            if((cities[i][1] == rootCityNumber) && (!connectedCities.containsKey(cities[i][0])) && (!hCities.containsKey(cities[i][0]))) {
                connectedCities.put(cities[i][0], rootCityNumber);
            }
        }
    }

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
    }
}
