// 
// Decompiled by Procyon v0.5.30
// 

public class IllegalInstructionException extends ExceptionException
{
    /**
	 * Generated SerialID
	 */
	private static final long serialVersionUID = -2848725183453712425L;

	public IllegalInstructionException(final String s) {
        super(s);
    }
    
    public String getExceptionDescription() {
        return "IllegalInstructionException: " + this.getMessage();
    }
}
