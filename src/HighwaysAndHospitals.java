import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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
        long mCost;
        Map<Integer, Integer> hCities = new HashMap<Integer, Integer>();
        Map<Integer, Integer> connectedCities = new HashMap<Integer, Integer>();
        int maxValue = n+1;
        int mostConnectedCity;
        int connectedCityNum;
        for(int i = 0; i < cities.length; i++){
            mostConnectedCity = getMostConnectedCity(maxValue, cities);
            connectedCityNum = getNumOfConnectedCities(mostConnectedCity, cities);
            if(connectedCities.containsKey(mostConnectedCity)){
                addConnectedCities(connectedCities, cities, mostConnectedCity);
            }
            else{
                if(!(hCities.containsKey(mostConnectedCity)))
                {
                    hCities.put(mostConnectedCity, connectedCityNum);
                    if(!(connectedCities.containsKey(mostConnectedCity))){
                        addConnectedCities(connectedCities, cities, mostConnectedCity);
                    }
                }
            }
            maxValue = connectedCityNum;
        }
        for(int i = 1; i < n+1; i++){
            if(!hCities.containsKey(i) && !connectedCities.containsKey(i)){
                hCities.put(i, 0);
            }
        }
        int numHospitals = hCities.size();
        int numHighways = cities.length;
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

    public static void addConnectedCities(Map<Integer, Integer> connectedCities, int cities[][], int rootCityNumber){
        for(int i = 0; i < cities.length; i++){
            if(cities[i][0] == rootCityNumber){
                connectedCities.put(cities[i][1], rootCityNumber);
            }
            if(cities[i][1] == rootCityNumber){
                connectedCities.put(cities[i][0], rootCityNumber);
            }
        }
    }

    public static int getMostConnectedCity(int maxValue, int cities[][]){
        int answer = 0;
        int mostConnected = 0;
        int mostConnectedCity = 0;
        for(int i = 0; i < cities.length; i++){
            answer = getNumOfConnectedCities(cities[i][0], cities);
            if((mostConnected < answer) && (answer < maxValue)){
                mostConnected = answer;
                mostConnectedCity = cities[i][0];
            }
            answer = getNumOfConnectedCities(cities[i][1], cities);
            if((mostConnected < answer) && (answer < maxValue)){
                mostConnected = answer;
                mostConnectedCity = cities[i][1];
            }
        }
        return mostConnectedCity;
    }

    public static int maxHighways(int cities[][])
    {
        return (cities.length - 1);
    }

    public static long costForSolution(int n, int cities[][], int hospitalCost, int highwayCost, int numHospitals, int numHighways){
        long answer;
        if((numHospitals < 1) || (numHospitals > n)){
            return -1;
        }
        if((numHighways < 0) || (numHighways > maxHighways(cities))){
            return -1;
        }
        answer = (hospitalCost * numHospitals) + (highwayCost * numHighways);
        return answer;
    }
}
