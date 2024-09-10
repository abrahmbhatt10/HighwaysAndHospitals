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

    public int maxHospitals(int n)
    {
        return n;
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

    public static int minNumHospitalsNeeded(int n, int cities[][]){
        int connected[] = new int[n+1];
        for(int i = 0; i < n+1; i++){
            connected[i] = 0;
        }
        for(int j = 0; j < cities.length; j++){
           connected[cities[j][0]]++;
           connected[cities[j][1]]++;
        }
        int mostConnected = -1;
        int mostConnectedCity = 0;
        for(int i = 0; i < i+1; i++){
            if(mostConnected < connected[i]){
                mostConnected = connected[i];
                mostConnectedCity = i;
            }
        }

    }

}
