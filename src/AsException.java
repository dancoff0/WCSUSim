// 
// Decompiled by Procyon v0.5.30
// 

class AsException extends Exception
{
    /**
	 * Generated SerialID
	 */
	private static final long serialVersionUID = -1299640770849672386L;
	public Instruction insn;
    
    AsException(final Instruction insn, final String s) {
        super(s);
        this.insn = insn;
    }
    
    AsException(final String s) {
        super(s);
    }
    
    public String getMessage() {
        String string = "Assembly error: ";
        if (this.insn != null) {
            string = string + "[line " + this.insn.getLineNumber() + ", '" + this.insn.getOriginalLine() + "']: ";
        }
        return string + super.getMessage();
    }
}
