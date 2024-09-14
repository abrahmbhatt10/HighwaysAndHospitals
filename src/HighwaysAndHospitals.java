/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: [Agastya Brahmbhatt]
 *
 */

public class HighwaysAndHospitals {

    /*
     *  cost() returns the minimum cost to provide
     *  hospital access for all citizens in the county.
     * The formula for cost is C * hospitalcost + (n - c) * highwaycost
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        /*
            Checks for the special case that hospitals are cheaper than highways.
         */
        if(hospitalCost < highwayCost){
            return hospitalCost * n;
        }
        int numberOfClusters = getNumberOfClusters(n, cities);
        return ((numberOfClusters * hospitalCost) + (n - numberOfClusters) * highwayCost);
    }

    public static int getNumberOfClusters(int n, int cities[][]){
        int[] mapHighways= new int[n + 1];
        int root = 0;
        int branch = 0;
        int subroot = 0;
        int subRoot1 = 0;
        int numClusters = 0;
        for(int i = 0; i < n + 1; i++){
            mapHighways[i] = 0;
        }
        for(int i = 0; i < cities.length; i++){
            if(cities[i][0] < cities[i][1]){
                root = cities[i][0];
                branch = cities[i][1];
            }
            else{
                root = cities[i][1];
                branch = cities[i][0];
            }
            if(mapHighways[branch] == 0){
                mapHighways[branch] = root;
            }
            else{
                subroot = mapHighways[branch];
                while((root < subroot) && (subroot != 0)){
                    subRoot1 = mapHighways[subroot];
                    subroot = subRoot1;
                }
                if((root < subroot) && (mapHighways[subroot] == 0)){
                    mapHighways[subroot] = root;
                }
                else{
                    /*
                        SubRoot is less than root.
                     */
                    mapHighways[root] = subroot;
                }
            }
        }
        for(int i = 1; i < mapHighways.length; i++){
            if(mapHighways[i] == 0){
                numClusters++;
            }
        }
        return numClusters;
    }
}