import java.util.LinkedList;
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
        int numHospitals;
        Queue<Integer> hCities = new LinkedList<Integer>();
        Queue<Integer> connectedCities = new LinkedList<Integer>();
        int maxValue = n+1;
        int mostConnectedCity;
        for(int i = 0; i < cities.length; i++){
            mostConnectedCity = getMostConnectedCity(maxValue, cities);
            if()
            hCities.add(mostConnectedCity);
            addConnectedCity(connectedCities, cities, mostConnectedCity);
            maxValue = getNumOfConnectedCities(mostConnectedCity,cities);
        }
        for(int i = 0; i < )
        for(int numHighways = 0; numHighways < maxHighways(cities) + 1; numHighways++){
            if(numHighways <= n){
                numHospitals = n - numHighways;
            }
            else{
                numHospitals = 1;
            }
            mCost = costForSolution(n, cities, hospitalCost, highwayCost, numHospitals, numHighways);
            if((mCost < minCost) || (minCost == 0)){
                minCost = mCost;
            }
        }
        return minCost;
    }

    public static boolean isExists(){

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

    public static void addConnectedCity(Queue<Integer> connectedCities, int cities[][], int rootCityNumber){
        for(int i = 0; i < cities.length; i++){
            if(cities[i][0] == rootCityNumber){
                connectedCities.add(cities[i][1]);
            }
            if(cities[i][1] == rootCityNumber){
                connectedCities.add(cities[i][0]);
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
