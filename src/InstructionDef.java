import java.util.List;

// 
// Decompiled by Procyon v0.5.30
// 

public abstract class InstructionDef
{
    private String opcode;
    private String format;
    Location dReg;
    Location sReg;
    Location tReg;
    Location signedImmed;
    Location pcOffset;
    Location unsignedImmed;
    private int mask;
    private int match;
    
    public InstructionDef() {
        this.opcode = null;
        this.format = new String();
        this.dReg = new Location();
        this.sReg = new Location();
        this.tReg = new Location();
        this.signedImmed = new Location();
        this.pcOffset = new Location();
        this.unsignedImmed = new Location();
        this.mask = 0;
        this.match = 0;
    }
    
    public int execute(final Word word, final int n, final RegisterFile registerFile, final Memory memory, final Machine machine) throws IllegalMemAccessException, IllegalInstructionException {
        throw new IllegalInstructionException("Abstract instruction (or pseudo-instruction)");
    }
    
    public boolean isDataDirective() {
        return false;
    }
    
    public boolean isCall() {
        return false;
    }
    
    public boolean isBranch() {
        return false;
    }
    
    public boolean isLoad() {
        return false;
    }
    
    public boolean isStore() {
        return false;
    }
    
    public int getRefAddr(final Word word, final int n, final RegisterFile registerFile, final Memory memory) throws IllegalMemAccessException {
        return 0;
    }
    
    public final String getOpcode() {
        return this.opcode;
    }
    
    public final void setOpcode(final String opcode) {
        this.opcode = opcode;
    }
    
    public String getFormat() {
        return (this.opcode.toUpperCase() + " " + this.format).trim();
    }
    
    public int getNextAddress(final Instruction instruction) throws AsException {
        return instruction.getAddress() + 1;
    }
    
    public int getDestinationReg(final Word word) {
        return -1;
    }
    
    public int getSourceReg1(final Word word) {
        return -1;
    }
    
    public int getSourceReg2(final Word word) {
        return -1;
    }
    
    public final int getDReg(final Word word) {
        ISA.check(this.dReg.valid, "Invalid register");
        return word.getZext(this.dReg.start, this.dReg.end);
    }
    
    public final int getSReg(final Word word) {
        ISA.check(this.sReg.valid, "Invalid register");
        return word.getZext(this.sReg.start, this.sReg.end);
    }
    
    public final int getTReg(final Word word) {
        ISA.check(this.tReg.valid, "Invalid register");
        return word.getZext(this.tReg.start, this.tReg.end);
    }
    
    public final int getSignedImmed(final Word word) {
        return word.getSext(this.signedImmed.start, this.signedImmed.end);
    }
    
    public final int getPCOffset(final Word word) {
        return word.getSext(this.pcOffset.start, this.pcOffset.end);
    }
    
    public final int getUnsignedImmed(final Word word) {
        return word.getZext(this.unsignedImmed.start, this.unsignedImmed.end);
    }
    
    public String disassemble(final Word word, final int n, final Machine machine) {
        int n2 = 1;
        String s = this.getOpcode();
        if (this.dReg.valid) {
            String s2;
            if (n2 != 0) {
                s2 = s + " ";
                n2 = 0;
            }
            else {
                s2 = s + ", ";
            }
            s = s2 + "R" + this.getDReg(word);
        }
        if (this.sReg.valid) {
            String s3;
            if (n2 != 0) {
                s3 = s + " ";
                n2 = 0;
            }
            else {
                s3 = s + ", ";
            }
            s = s3 + "R" + this.getSReg(word);
        }
        if (this.tReg.valid) {
            String s4;
            if (n2 != 0) {
                s4 = s + " ";
                n2 = 0;
            }
            else {
                s4 = s + ", ";
            }
            s = s4 + "R" + this.getTReg(word);
        }
        if (this.signedImmed.valid) {
            String s5;
            if (n2 != 0) {
                s5 = s + " ";
                n2 = 0;
            }
            else {
                s5 = s + ", ";
            }
            s = s5 + "#" + this.getSignedImmed(word);
        }
        if (this.pcOffset.valid) {
            String s6;
            if (n2 != 0) {
                s6 = s + " ";
                n2 = 0;
            }
            else {
                s6 = s + ", ";
            }
            final int n3 = n + this.getPCOffset(word) + 1;
            String lookupSym = null;
            if (machine != null) {
                lookupSym = machine.lookupSym(n3);
            }
            if (lookupSym != null) {
                s = s6 + lookupSym;
            }
            else {
                s = s6 + Word.toHex(n3);
            }
        }
        if (this.unsignedImmed.valid) {
            String s7;
            if (n2 != 0) {
                s7 = s + " ";
            }
            else {
                s7 = s + ", ";
            }
            s = s7 + "x" + Integer.toHexString(this.getUnsignedImmed(word)).toUpperCase();
        }
        return s;
    }
    
    public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
        final Word word = new Word();
        word.setValue(this.match);
        try {
            int n = 0;
            if (this.dReg.valid) {
                word.setUnsignedField(instruction.getRegs(n), this.dReg.start, this.dReg.end);
                ++n;
            }
            if (this.sReg.valid) {
                word.setUnsignedField(instruction.getRegs(n), this.sReg.start, this.sReg.end);
                ++n;
            }
            if (this.tReg.valid) {
                word.setUnsignedField(instruction.getRegs(n), this.tReg.start, this.tReg.end);
                ++n;
            }
        }
        catch (AsException ex) {
            throw new AsException(instruction, "Register number out of range");
        }
        try {
            if (this.signedImmed.valid) {
                word.setSignedField(instruction.getOffsetImmediate(), this.signedImmed.start, this.signedImmed.end);
            }
            if (this.unsignedImmed.valid) {
                word.setUnsignedField(instruction.getOffsetImmediate(), this.unsignedImmed.start, this.unsignedImmed.end);
            }
        }
        catch (AsException ex2) {
            throw new AsException(instruction, "Immediate out of range");
        }
        if (this.pcOffset.valid) {
            final int lookup = symTab.lookup(instruction.getLabelRef());
            if (lookup == -1) {
                throw new AsException(instruction, "Undeclared label: " + instruction.getLabelRef());
            }
            instruction.setOffsetImmediate(lookup - (instruction.getAddress() + 1));
            try {
                word.setSignedField(instruction.getOffsetImmediate(), this.pcOffset.start, this.pcOffset.end);
            }
            catch (AsException ex3) {
                throw new AsException(instruction, "PC-relative offset out of range");
            }
        }
        list.add(word);
    }
    
    private String encodeField(final String s, final char c, final String s2, final Location location) {
        final int index = s.indexOf(c);
        final int lastIndex = s.lastIndexOf(c);
        if (index != -1 && lastIndex != -1) {
            ISA.check(s.substring(index, lastIndex).matches("[" + c + "]*"), "Strange encoding of '" + c + "': " + s);
            location.valid = true;
            location.start = 15 - index;
            location.end = 15 - lastIndex;
            this.format = this.format + s2 + " ";
            return s.replaceAll("" + c, "x");
        }
        return s;
    }
    
    public final boolean match(final Word word) {
        return (word.getValue() & this.mask) == this.match;
    }
    
    public final void setEncoding(String s) {
        final String s2 = s;
        s = s.toLowerCase();
        s = s.replaceAll("\\s", "");
        s = s.replaceAll("[^x10iudstpz]", "");
        ISA.check(s.length() == 16, "Strange encoding: " + s2);
        s = this.encodeField(s, 'd', "Reg", this.dReg);
        s = this.encodeField(s, 's', "Reg", this.sReg);
        s = this.encodeField(s, 't', "Reg", this.tReg);
        s = this.encodeField(s, 'i', "Num", this.signedImmed);
        s = this.encodeField(s, 'p', "Label", this.pcOffset);
        s = this.encodeField(s, 'u', "Num", this.unsignedImmed);
        s = this.encodeField(s, 'z', "String", this.unsignedImmed);
        s = s.replaceAll("[^x10]", "");
        ISA.check(s.length() == 16, "Strange encoding: " + s2);
        this.mask = Integer.parseInt(s.replaceAll("0", "1").replaceAll("x", "0"), 2);
        this.match = Integer.parseInt(s.replaceAll("x", "0"), 2);
    }
    
    class Location
    {
        public boolean valid;
        public int start;
        public int end;
        
        Location() {
            this.valid = false;
            this.start = -1;
            this.end = -1;
        }
    }
}
