import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation  {
	private int size;
	private WeightedQuickUnionUF grid;
	private WeightedQuickUnionUF full;
	private boolean[] open;
	private int virtualTop;
	private int virtualBottom;
	// the constructor
	public Percolation(int n) {
		if (n<=0)
			 throw new java.lang.IllegalArgumentException
				("n can not be less than zero");
		size = n;
		// create two weighted quick union find to avoid backwash problem
		// two for virtual top and button 
		grid = new WeightedQuickUnionUF(size*size + 2);
		full = new WeightedQuickUnionUF(size*size + 1);
		open = new boolean[size*size];
		virtualTop = getQFIndex(size, size) + 1;
		virtualBottom = getQFIndex(size, size) + 2;
		
	}
	// open site (row, col) if it is not open already
	public void open(int row, int col) {

		handleoutofBounds(row, col);
		// the site is already open
		if (isOpen(row, col)) return;

		int index = getQFIndex(row, col);
		open[index] = true;
		// connect the first row to virtualTop
		if (row == 1) {
			grid.union(virtualTop, index);
			full.union(virtualTop, index);
		}
		// connect the last row to virtualBottom
		// full does not connect to virtualBottom to avoid backwash
		if (row == size) 
			grid.union(virtualBottom, index);
		// There are four neighboring sites for each site
		// Detect all of them whtehter they are open
		if (isValid(row-1, col) && isOpen(row-1, col)) {
			grid.union(index, getQFIndex(row-1, col));
			full.union(index, getQFIndex(row-1, col));
		}
		if (isValid(row+1, col) && isOpen(row+1, col)) {
			grid.union(index, getQFIndex(row+1, col));
			full.union(index, getQFIndex(row+1, col));
		}
		if (isValid(row, col-1) && isOpen(row, col-1)) {
			grid.union(index, getQFIndex(row, col-1));
			full.union(index, getQFIndex(row, col-1));
		}
		if (isValid(row, col+1) && isOpen(row, col+1)) {
			grid.union(index, getQFIndex(row, col+1));
			full.union(index, getQFIndex(row, col+1));
		}
	}
	// return the result of whether the site (row, col) is open.
	public boolean isOpen(int row, int col) {
		handleoutofBounds(row, col);
		return open[getQFIndex(row, col)];
	}
	// return the result of whether the site (row, col) is connected to the top row.
	public boolean isFull(int row, int col){
		handleoutofBounds(row, col);
		return full.connected(virtualTop, getQFIndex(row, col));
        
	}
	public int numberOfOpenSites(){
		int count = 0;
		for(int i = 0; i < virtualTop; i++)
			if(open[i]) count++;
			
		return count;
	}
	// return the result of whether the site (row, col) percolates.
	public boolean percolates() {
		return grid.connected(virtualTop, virtualBottom);
	}

	private void handleoutofBounds(int row,  int col) {
		if (!isValid(row, col))
			throw new java.lang.IndexOutOfBoundsException();
	}

	private int getQFIndex(int row, int col) {
		return (size*(row-1) + col) -1;
	}

	private boolean isValid(int row, int col) {
        return row > 0 && col > 0 
        	&& row <= size && col <= size;
    }
}
