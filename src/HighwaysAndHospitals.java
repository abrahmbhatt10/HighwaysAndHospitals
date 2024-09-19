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
            // This figures out the minConnected = ((long) highwayCost)/((long)(highwayCost-hospitalCost));
            // Multiples by minConnected *= (long)n;
            // Checks if(minConnected >= (numberOfClusters)), t
            //{
                numberOfClusters = (long)n;
            //}
        }

        // Returns the "Number of Clusters "+numberOfClusters+" hospitalCost "+hospitalCost+" and cities "+n+" highwayCost "+highwayCost
        mCost = ((long)(n - numberOfClusters) * (long)highwayCost)+ (((long)numberOfClusters * (long) hospitalCost));
        return mCost;
    }

    public static int getRoot(int mapHighways[], int branch){
        if(mapHighways[branch] <= 0){
            return branch;
        }
        // Returns the getRoot of "+branch);
        int parent = mapHighways[branch];
        int grandParent = 0;
        /*
            This code traverses each subRoot to figure out which is the final node, and thus can
            provide insight as to the number of clusters within a given set.
         */
        while(mapHighways[parent] > 0){
            grandParent = mapHighways[parent];
            parent = grandParent;
        }
        // Returns the parent "+parent+" for branch "+branch
        return parent;
    }

    /*
        This gets the number of clusters using the Union find algorithm,
        A convenient way to take each edge, map it to a subRoot, and eventually
        map all the subRoots to n number of final roots, which will represent
        our two separate clusters.
     */
    public static long getNumberOfClusters(int n, int cities[][]){
        int[] mapHighways= new int[n + 1];
        int root = 0;
        int branch = 0;
        int rootOfBranch = 0;
        int rootOfRoot= 0;
        int numClusters = 0;
        /*
            Fills mapHighways[] with 0's.
         */
        for(int i = 1; i < n + 1; i++){
            mapHighways[i] = 0;
        }
        /*
            Assigns subRoots for each branch.
         */
        for(int i = 0; i < cities.length; i++){
            /*
                Checks if a city is a root or branch, and if its equal, return 0.
             */
            if(cities[i][0] < cities[i][1]){
                root = cities[i][0];
                branch = cities[i][1];
            }
            else{
                root = cities[i][1];
                branch = cities[i][0];
            }
            /*
                Plots each subRoot in the mapHighways array, and keeps going down
                Row by row to plot each subRoot. At the end, there will be
                n blank spaces remaining, hinting at n clusters.
             */
            rootOfBranch = getRoot(mapHighways,branch);
            rootOfRoot = getRoot(mapHighways,root);
            if(rootOfRoot < rootOfBranch)
            {
                mapHighways[rootOfBranch] = rootOfRoot;
                mapHighways[rootOfRoot]--;
            }
            else if(rootOfBranch < rootOfRoot)
            {
                mapHighways[rootOfRoot] = rootOfBranch;
                mapHighways[rootOfBranch]--;
            }
        }
        /*
            Uses path compression to make the code more efficient.
         */
        weightBalancing(mapHighways, cities);
        pathCompression(mapHighways, cities);
        numClusters = 0;
        /*
            Gets and returns the number of clusters using the 0's in the bottom row.
         */
        for(int i = 1; i < mapHighways.length; i++){
            if(mapHighways[i] <= 0){
                numClusters++;
            }
        }
        return numClusters;
    }

    /*
    (The below pseudocode was taken from Mr. Blick's slides):
    For each edge AB:
	X = A
	While city X is not its root:
		X = roots[X]
	While city A is not its root:
		temp = roots[A]
		roots[A] = X
		A = temp

		The path compression algorithm makes each branch and subBranch a direct branch to the main root.
     */
    public static void pathCompression(int[] mapHighways, int[][] cities){
        int edgeX;
        int temp;
        for (int i = 0; i < cities.length; i++){
            for(int j = 0; j < cities[i].length; j++){
                edgeX = cities[i][j];
                while(edgeX != getRoot(mapHighways,edgeX)){
                    edgeX = getRoot(mapHighways,edgeX);
                    while(cities[i][j] != getRoot(mapHighways,cities[i][j])){
                        temp = getRoot(mapHighways,cities[i][j]);
                        setRoot(mapHighways, cities[i][j], edgeX);
                        cities[i][j] = temp;
                    }
                }
            }
        }
    }

    // The below function simply sets the branch at mapHighways to the root passed.
    public static void setRoot(int[] mapHighways, int branch, int root){
        mapHighways[branch] = root;
    }

    /*
    For each edge AB:
	// Find roots, R and S
	X = order(R)
	Y = order(S)
	if (X > Y)
		root[S] = R
	else
		root[R] = S
     */

    public static void weightBalancing(int[] mapHighways, int[][] cities){
        int weightX;
        int weightY;
        int R;
        int S;
        for (int i = 0; i < cities.length; i++) {
            // Find roots, R and S
            R = getRoot(mapHighways, cities[i][0]);
            S = getRoot(mapHighways, cities[i][1]);
            weightX = mapHighways[R];
            weightY = mapHighways[S];
            if (Math.abs(weightX) > Math.abs(weightY))
                setRoot(mapHighways, S, R);
            else
                setRoot(mapHighways, R, S);
        }
    }
}