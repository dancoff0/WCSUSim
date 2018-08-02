// 
// Decompiled by Procyon v0.5.30
// 

public class BranchPredictor
{
  private int[][] predictor;
  private int indexMask;

  public BranchPredictor()
  {
    this.indexMask = 0;
  }

  public BranchPredictor( final int n )
  {
    this.indexMask = 0;
    int n2 = 0;
    int n3 = n;
    final boolean b = true;
    for( int i = 0; i < 16; ++i )
    {
      if( (n3 & (b ? 1 : 0)) == (b ? 1 : 0) )
      {
        ++n2;
        for( int j = 0; j < i; ++j )
        {
          this.indexMask <<= 1;
          this.indexMask |= 0x1;
        }
      }
      n3 >>= 1;
    }
    if( n2 != 1 )
    {
      throw new IllegalArgumentException( "Branch predictor size must be a power of two." );
    }
    this.predictor = new int[n][2];
  }

  public int getPredictedPC( final int n )
  {
    final int n2 = n & this.indexMask;
    int n3 = n + 1;
    if( this.predictor[n2][0] == n )
    {
      n3 = this.predictor[n2][1];
    }
    return n3;
  }

  public void update( final int n, final int n2 )
  {
    this.predictor[n & this.indexMask][0] = n;
    this.predictor[n & this.indexMask][1] = n2;
  }

  public String toString()
  {
    String string = new String( "" );
    for( int i = 0; i < this.predictor.length; ++i )
    {
      string = string + String.valueOf( i ) + ":" + " tag: " + this.predictor[i][0] + " pred: " + this.predictor[i][1];
    }
    return string;
  }

  public void reset()
  {
    for( int i = 0; i < this.predictor.length; ++i )
    {
      this.predictor[i][0] = 0;
      this.predictor[i][1] = 0;
    }
  }
}
