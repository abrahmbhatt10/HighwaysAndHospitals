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

        long minConnected= 0;
        long numberOfClusters=0;
        long mCost;

        numberOfClusters = getNumberOfClusters(n, cities);

        if(highwayCost > hospitalCost)
        {
            //minConnected = ((long) highwayCost)/((long)(highwayCost-hospitalCost));
            //minConnected *= (long)n;
            //if(minConnected >= (numberOfClusters))
            //{
                numberOfClusters = (long)n;
            //}
        }

        //System.out.println("Number of Clusters "+numberOfClusters+" hospitalCost "+hospitalCost+" and cities "+n+" highwayCost "+highwayCost);
        mCost = ((long)(n - numberOfClusters) * (long)highwayCost)+ (((long)numberOfClusters * (long) hospitalCost));
        return mCost;
    }

    public static int getRoot(int mapHighways[], int branch){
        if(mapHighways[branch] == 0){
            return branch;
        }
        //System.out.println("getRoot of "+branch);
        int parent = mapHighways[branch];
        int grandParent = 0;
        while(mapHighways[parent] != 0){
            grandParent = mapHighways[parent];
            //System.out.println("grand parent "+grandParent);
            parent = grandParent;
            //System.out.println("parent "+parent);
        }
        //System.out.println("Return parent "+parent+" for branch "+branch);
        return parent;
    }
    public static long getNumberOfClusters(int n, int cities[][]){
        int[] mapHighways= new int[n + 1];
        int root = 0;
        int branch = 0;
        int rootOfBranch = 0;
        int rootOfRoot= 0;
        int numClusters = 0;
        for(int i = 1; i < n + 1; i++){
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
            if(root == branch)
            {
                System.out.print("Error Root "+root+" is equal to "+branch);
                return 0;
            }
            rootOfBranch = getRoot(mapHighways,branch);
            rootOfRoot = getRoot(mapHighways,root);
            if(rootOfRoot < rootOfBranch)
            {
                mapHighways[rootOfBranch] = rootOfRoot;
            }
            else if(rootOfBranch < rootOfRoot)
            {
                mapHighways[rootOfRoot] = rootOfBranch;
            }
        }
        numClusters = 0;
        for(int i = 1; i < mapHighways.length; i++){
            if(mapHighways[i] == 0){
                numClusters++;
            }
        }
        return numClusters;
    }
}