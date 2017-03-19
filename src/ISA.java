import java.util.List;
import java.util.Hashtable;
import java.util.HashSet;

// 
// Decompiled by Procyon v0.5.30
// 

public class ISA
{
    public static InstructionDef[] lookupTable;
    public static HashSet<String> opcodeSet;
    public static Hashtable<String, InstructionDef> formatToDef;
    
    public static void execute(final RegisterFile registerFile, final Memory memory, final Machine machine) throws IllegalMemAccessException, IllegalInstructionException {
        final int pc = registerFile.getPC();
        registerFile.checkAddr(pc);
        final Word inst = memory.getInst(pc);
        final InstructionDef instructionDef = ISA.lookupTable[inst.getValue()];
        if (instructionDef == null) {
            throw new IllegalInstructionException("Undefined instruction:  " + inst.toHex());
        }
        final int execute = instructionDef.execute(inst, pc, registerFile, memory, machine);
        registerFile.setPC(execute);
        ++machine.CYCLE_COUNT;
        ++machine.INSTRUCTION_COUNT;
        if (execute != machine.getBranchPredictor().getPredictedPC(pc)) {
            machine.CYCLE_COUNT += 2;
            machine.BRANCH_STALL_COUNT += 2;
            machine.getBranchPredictor().update(pc, execute);
        }
        if (instructionDef.isLoad()) {
            final Word inst2 = machine.getMemory().getInst(execute);
            final InstructionDef instructionDef2 = ISA.lookupTable[inst2.getValue()];
            if (instructionDef2 == null) {
                throw new IllegalInstructionException("Undefined instruction:  " + inst2.toHex());
            }
            if (!instructionDef2.isStore()) {
                final int destinationReg = instructionDef.getDestinationReg(inst);
                if (destinationReg >= 0 && (destinationReg == instructionDef2.getSourceReg1(inst2) || destinationReg == instructionDef2.getSourceReg2(inst2))) {
                    ++machine.CYCLE_COUNT;
                    ++machine.LOAD_STALL_COUNT;
                }
            }
        }
        if (machine.isTraceEnabled()) {
            machine.generateTrace(instructionDef, pc, inst);
        }
    }
    
    public static String disassemble(final Word word, final int n, final Machine machine) {
        if (!machine.lookupAddrToInsn(n) && !PennSim.isLC3()) {
            return "";
        }
        final InstructionDef instructionDef = ISA.lookupTable[word.getValue()];
        if (instructionDef == null) {
            return ".FILL " + word.toHex();
        }
        return instructionDef.disassemble(word, n, machine);
    }
    
    public static boolean isOpcode(final String s) {
        return ISA.opcodeSet.contains(s.toUpperCase());
    }
    
    public static void checkFormat(final Instruction instruction, final int n) throws AsException {
        if (ISA.formatToDef.get(instruction.getFormat()) == null) {
            throw new AsException(instruction, "Unexpected instruction format: actual: '" + instruction.getFormat() + "'");
        }
    }
    
    public static void encode(final Instruction instruction, final List<Word> list) throws AsException {
        final String format = instruction.getFormat();
        if (ISA.formatToDef.get(format) == null) {
            instruction.error("Unknown instruction format: " + format);
        }
    }
    
    public static boolean isCall(final Word word) throws IllegalInstructionException {
        final InstructionDef instructionDef = ISA.lookupTable[word.getValue()];
        if (instructionDef != null) {
            return instructionDef.isCall();
        }
        throw new IllegalInstructionException("Undefined instruction:  " + word.toHex());
    }
    
    public static void createDef(final String opcode, final String encoding, final InstructionDef instructionDef) {
        instructionDef.setOpcode(opcode);
        if (encoding != null) {
            instructionDef.setEncoding(encoding);
            if (!instructionDef.isDataDirective()) {
                int n = 0;
                int n2 = 0;
                for (int i = 0; i < 65535; ++i) {
                    if (instructionDef.match(new Word(i))) {
                        if (ISA.lookupTable[i] == null) {
                            ++n;
                            ISA.lookupTable[i] = instructionDef;
                        }
                        else {
                            ++n2;
                        }
                    }
                }
                check(n > 0 || n2 > 0, "Useless instruction defined, probably an error, opcode=" + opcode);
            }
        }
        ISA.formatToDef.put(instructionDef.getFormat(), instructionDef);
        ISA.opcodeSet.add(instructionDef.getOpcode().toUpperCase());
    }
    
    public static void check(final boolean b, final String s) {
        if (!b) {
            throw new InternalException(s);
        }
    }
    
    protected static void labelRefToPCOffset(final SymTab symTab, final Instruction instruction, final int n) throws AsException {
        final int n2 = instruction.getAddress() + 1;
        final int lookup = symTab.lookup(instruction.getLabelRef());
        final int offsetImmediate = lookup - n2;
        if (lookup == -1) {
            throw new AsException(instruction, "Undeclared label '" + instruction.getLabelRef() + "'");
        }
        if (offsetImmediate < -(1 << n - 1) || offsetImmediate > 1 << n - 1) {
            throw new AsException(instruction, "Jump offset longer than " + n + " bits");
        }
        instruction.setOffsetImmediate(offsetImmediate);
    }
    
    protected void init() {
        createDef(".ORIG", "xxxx iiiiiiiiiiii", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
                if (list.size() != 0) {
                    throw new AsException(".ORIG can only appear at the beginning of a file");
                }
                list.add(new Word(instruction.getOffsetImmediate()));
            }
            
            public boolean isDataDirective() {
                return true;
            }
            
            public int getNextAddress(final Instruction instruction) throws AsException {
                return instruction.getOffsetImmediate();
            }
        });
        createDef(".FILL", "xxxx iiiiiiiiiiii", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
                list.add(new Word(instruction.getOffsetImmediate()));
            }
            
            public boolean isDataDirective() {
                return true;
            }
        });
        createDef(".FILL", "xxxx pppppppppppp", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
                final int lookup = symTab.lookup(instruction.getLabelRef());
                if (lookup == -1) {
                    throw new AsException(instruction, "Undeclared label: '" + instruction.getLabelRef() + "'");
                }
                list.add(new Word(lookup));
            }
            
            public boolean isDataDirective() {
                return true;
            }
        });
        createDef(".BLKW", "xxxx iiiiiiiiiiii", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
                for (int offsetImmediate = instruction.getOffsetImmediate(), i = 0; i < offsetImmediate; ++i) {
                    list.add(new Word(0));
                }
            }
            
            public boolean isDataDirective() {
                return true;
            }
            
            public int getNextAddress(final Instruction instruction) throws AsException {
                return instruction.getAddress() + instruction.getOffsetImmediate();
            }
        });
        createDef(".STRINGZ", "xxxx zzzzzzzzzzzz", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
                for (int i = 0; i < instruction.getStringz().length(); ++i) {
                    list.add(new Word(instruction.getStringz().charAt(i)));
                }
                list.add(new Word(0));
            }
            
            public boolean isDataDirective() {
                return true;
            }
            
            public int getNextAddress(final Instruction instruction) throws AsException {
                return instruction.getAddress() + instruction.getStringz().length() + 1;
            }
        });
        createDef(".END", "xxxx xxxxxxxxxxxx", new InstructionDef() {
            public void encode(final SymTab symTab, final Instruction instruction, final List<Word> list) throws AsException {
            }
            
            public boolean isDataDirective() {
                return true;
            }
            
            public int getNextAddress(final Instruction instruction) {
                return instruction.getAddress();
            }
        });
    }
    
    static {
        ISA.lookupTable = new InstructionDef[65536];
        ISA.opcodeSet = new HashSet<String>();
        ISA.formatToDef = new Hashtable<String, InstructionDef>();
    }
}
