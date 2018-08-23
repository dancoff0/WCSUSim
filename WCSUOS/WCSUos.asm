        .ORIG x0000

; the TRAP vector table
	.FILL BAD_TRAP	; x00
	.FILL BAD_TRAP	; x01
	.FILL BAD_TRAP	; x02
	.FILL BAD_TRAP	; x03
	.FILL BAD_TRAP	; x04
	.FILL BAD_TRAP	; x05
	.FILL BAD_TRAP	; x06
	.FILL BAD_TRAP	; x07
	.FILL BAD_TRAP	; x08
	.FILL BAD_TRAP	; x09
	.FILL BAD_TRAP	; x0A
	.FILL BAD_TRAP	; x0B
	.FILL BAD_TRAP	; x0C
	.FILL BAD_TRAP	; x0D
	.FILL BAD_TRAP	; x0E
	.FILL BAD_TRAP	; x0F
	.FILL BAD_TRAP	; x10
	.FILL BAD_TRAP	; x11
	.FILL BAD_TRAP	; x12
	.FILL BAD_TRAP	; x13
	.FILL BAD_TRAP	; x14
	.FILL BAD_TRAP	; x15
	.FILL BAD_TRAP	; x16
	.FILL BAD_TRAP	; x17
	.FILL BAD_TRAP	; x18
	.FILL BAD_TRAP	; x19
	.FILL BAD_TRAP	; x1A
	.FILL BAD_TRAP	; x1B
	.FILL BAD_TRAP	; x1C
	.FILL BAD_TRAP	; x1D
	.FILL BAD_TRAP	; x1E
	.FILL BAD_TRAP	; x1F
	.FILL TRAP_GETC	; x20
	.FILL TRAP_OUT	; x21
	.FILL TRAP_PUTS	; x22
	.FILL TRAP_IN	; x23
	.FILL TRAP_PUTSP ; x24        
	.FILL TRAP_HALT	; x25
	.FILL TRAP_CLEAR_CONSOLE ; x26
	.FILL BAD_TRAP	; x27
	.FILL BAD_TRAP	; x28
	.FILL BAD_TRAP	; x29
	.FILL BAD_TRAP	; x2A
	.FILL BAD_TRAP	; x2B
	.FILL BAD_TRAP	; x2C
	.FILL BAD_TRAP	; x2D
	.FILL BAD_TRAP	; x2E
	.FILL BAD_TRAP	; x2F
	.FILL TRAP_DRAW_RECT	; x30
	.FILL TRAP_DRAW_LINE    ; x31
	.FILL BAD_TRAP	; x32
	.FILL BAD_TRAP	; x33
	.FILL BAD_TRAP	; x34
	.FILL BAD_TRAP	; x35
	.FILL BAD_TRAP	; x36
	.FILL BAD_TRAP	; x37
	.FILL BAD_TRAP	; x38
	.FILL BAD_TRAP	; x39
	.FILL BAD_TRAP	; x3A
	.FILL BAD_TRAP	; x3B
	.FILL BAD_TRAP	; x3C
	.FILL BAD_TRAP	; x3D
	.FILL BAD_TRAP	; x3E
	.FILL BAD_TRAP	; x3F
	.FILL TRAP_ENABLE_INTERRUPTS  ; x40
	.FILL TRAP_DISABLE_INTERRUPTS ; x41
	.FILL TRAP_ATTACH_ISR	      ; x42
	.FILL TRAP_DETACH_ISR	      ; x43
	.FILL BAD_TRAP	; x44
	.FILL BAD_TRAP	; x45
	.FILL BAD_TRAP	; x46
	.FILL BAD_TRAP	; x47
	.FILL BAD_TRAP	; x48
	.FILL BAD_TRAP	; x49
	.FILL BAD_TRAP	; x4A
	.FILL BAD_TRAP	; x4B
	.FILL BAD_TRAP	; x4C
	.FILL BAD_TRAP	; x4D
	.FILL BAD_TRAP	; x4E
	.FILL BAD_TRAP	; x4F
	.FILL TRAP_MOUNT_DISK         ; x50
	.FILL TRAP_RAW_READ	          ; x51
	.FILL TRAP_COPY_WORDS         ; x52
	.FILL TRAP_OPEN_READ_FILE     ; x53
	.FILL TRAP_FIND_INODE	      ; x54
	.FILL TRAP_FIND_STRING_LENGTH ; x55
	.FILL BAD_TRAP	; x56
	.FILL BAD_TRAP	; x57
	.FILL BAD_TRAP	; x58
	.FILL BAD_TRAP	; x59
	.FILL BAD_TRAP	; x5A
	.FILL BAD_TRAP	; x5B
	.FILL BAD_TRAP	; x5C
	.FILL BAD_TRAP	; x5D
	.FILL BAD_TRAP	; x5E
	.FILL BAD_TRAP	; x5F
	.FILL BAD_TRAP	; x60
	.FILL BAD_TRAP	; x61
	.FILL BAD_TRAP	; x62
	.FILL BAD_TRAP	; x63
	.FILL BAD_TRAP	; x64
	.FILL BAD_TRAP	; x65
	.FILL BAD_TRAP	; x66
	.FILL BAD_TRAP	; x67
	.FILL BAD_TRAP	; x68
	.FILL BAD_TRAP	; x69
	.FILL BAD_TRAP	; x6A
	.FILL BAD_TRAP	; x6B
	.FILL BAD_TRAP	; x6C
	.FILL BAD_TRAP	; x6D
	.FILL BAD_TRAP	; x6E
	.FILL BAD_TRAP	; x6F
	.FILL BAD_TRAP	; x70
	.FILL BAD_TRAP	; x71
	.FILL BAD_TRAP	; x72
	.FILL BAD_TRAP	; x73
	.FILL BAD_TRAP	; x74
	.FILL BAD_TRAP	; x75
	.FILL BAD_TRAP	; x76
	.FILL BAD_TRAP	; x77
	.FILL BAD_TRAP	; x78
	.FILL BAD_TRAP	; x79
	.FILL BAD_TRAP	; x7A
	.FILL BAD_TRAP	; x7B
	.FILL BAD_TRAP	; x7C
	.FILL BAD_TRAP	; x7D
	.FILL BAD_TRAP	; x7E
	.FILL BAD_TRAP	; x7F
	.FILL BAD_TRAP	; x80
	.FILL BAD_TRAP	; x81
	.FILL BAD_TRAP	; x82
	.FILL BAD_TRAP	; x83
	.FILL BAD_TRAP	; x84
	.FILL BAD_TRAP	; x85
	.FILL BAD_TRAP	; x86
	.FILL BAD_TRAP	; x87
	.FILL BAD_TRAP	; x88
	.FILL BAD_TRAP	; x89
	.FILL BAD_TRAP	; x8A
	.FILL BAD_TRAP	; x8B
	.FILL BAD_TRAP	; x8C
	.FILL BAD_TRAP	; x8D
	.FILL BAD_TRAP	; x8E
	.FILL BAD_TRAP	; x8F
	.FILL BAD_TRAP	; x90
	.FILL BAD_TRAP	; x91
	.FILL BAD_TRAP	; x92
	.FILL BAD_TRAP	; x93
	.FILL BAD_TRAP	; x94
	.FILL BAD_TRAP	; x95
	.FILL BAD_TRAP	; x96
	.FILL BAD_TRAP	; x97
	.FILL BAD_TRAP	; x98
	.FILL BAD_TRAP	; x99
	.FILL BAD_TRAP	; x9A
	.FILL BAD_TRAP	; x9B
	.FILL BAD_TRAP	; x9C
	.FILL BAD_TRAP	; x9D
	.FILL BAD_TRAP	; x9E
	.FILL BAD_TRAP	; x9F
	.FILL BAD_TRAP	; xA0
	.FILL BAD_TRAP	; xA1
	.FILL BAD_TRAP	; xA2
	.FILL BAD_TRAP	; xA3
	.FILL BAD_TRAP	; xA4
	.FILL BAD_TRAP	; xA5
	.FILL BAD_TRAP	; xA6
	.FILL BAD_TRAP	; xA7
	.FILL BAD_TRAP	; xA8
	.FILL BAD_TRAP	; xA9
	.FILL BAD_TRAP	; xAA
	.FILL BAD_TRAP	; xAB
	.FILL BAD_TRAP	; xAC
	.FILL BAD_TRAP	; xAD
	.FILL BAD_TRAP	; xAE
	.FILL BAD_TRAP	; xAF
	.FILL BAD_TRAP	; xB0
	.FILL BAD_TRAP	; xB1
	.FILL BAD_TRAP	; xB2
	.FILL BAD_TRAP	; xB3
	.FILL BAD_TRAP	; xB4
	.FILL BAD_TRAP	; xB5
	.FILL BAD_TRAP	; xB6
	.FILL BAD_TRAP	; xB7
	.FILL BAD_TRAP	; xB8
	.FILL BAD_TRAP	; xB9
	.FILL BAD_TRAP	; xBA
	.FILL BAD_TRAP	; xBB
	.FILL BAD_TRAP	; xBC
	.FILL BAD_TRAP	; xBD
	.FILL BAD_TRAP	; xBE
	.FILL BAD_TRAP	; xBF
	.FILL BAD_TRAP	; xC0
	.FILL BAD_TRAP	; xC1
	.FILL BAD_TRAP	; xC2
	.FILL BAD_TRAP	; xC3
	.FILL BAD_TRAP	; xC4
	.FILL BAD_TRAP	; xC5
	.FILL BAD_TRAP	; xC6
	.FILL BAD_TRAP	; xC7
	.FILL BAD_TRAP	; xC8
	.FILL BAD_TRAP	; xC9
	.FILL BAD_TRAP	; xCA
	.FILL BAD_TRAP	; xCB
	.FILL BAD_TRAP	; xCC
	.FILL BAD_TRAP	; xCD
	.FILL BAD_TRAP	; xCE
	.FILL BAD_TRAP	; xCF
	.FILL BAD_TRAP	; xD0
	.FILL BAD_TRAP	; xD1
	.FILL BAD_TRAP	; xD2
	.FILL BAD_TRAP	; xD3
	.FILL BAD_TRAP	; xD4
	.FILL BAD_TRAP	; xD5
	.FILL BAD_TRAP	; xD6
	.FILL BAD_TRAP	; xD7
	.FILL BAD_TRAP	; xD8
	.FILL BAD_TRAP	; xD9
	.FILL BAD_TRAP	; xDA
	.FILL BAD_TRAP	; xDB
	.FILL BAD_TRAP	; xDC
	.FILL BAD_TRAP	; xDD
	.FILL BAD_TRAP	; xDE
	.FILL BAD_TRAP	; xDF
	.FILL BAD_TRAP	; xE0
	.FILL BAD_TRAP	; xE1
	.FILL BAD_TRAP	; xE2
	.FILL BAD_TRAP	; xE3
	.FILL BAD_TRAP	; xE4
	.FILL BAD_TRAP	; xE5
	.FILL BAD_TRAP	; xE6
	.FILL BAD_TRAP	; xE7
	.FILL BAD_TRAP	; xE8
	.FILL BAD_TRAP	; xE9
	.FILL BAD_TRAP	; xEA
	.FILL BAD_TRAP	; xEB
	.FILL BAD_TRAP	; xEC
	.FILL BAD_TRAP	; xED
	.FILL BAD_TRAP	; xEE
	.FILL BAD_TRAP	; xEF
	.FILL BAD_TRAP	; xF0
	.FILL BAD_TRAP	; xF1
	.FILL BAD_TRAP	; xF2
	.FILL BAD_TRAP	; xF3
	.FILL BAD_TRAP	; xF4
	.FILL BAD_TRAP	; xF5
	.FILL BAD_TRAP	; xF6
	.FILL BAD_TRAP	; xF7
	.FILL BAD_TRAP	; xF8
	.FILL BAD_TRAP	; xF9
	.FILL BAD_TRAP	; xFA
	.FILL BAD_TRAP	; xFB
	.FILL BAD_TRAP	; xFC
	.FILL BAD_TRAP	; xFD
	.FILL BAD_TRAP	; xFE
	.FILL BAD_TRAP	; xFF

; the interrupt vector table
; interrupts are not currently implemented
INTERRUPT_VECTOR_TABLE
    .FILL BAD_INT	; x00
	.FILL BAD_INT	; x01
	.FILL BAD_INT	; x02
	.FILL BAD_INT	; x03
	.FILL BAD_INT	; x04
	.FILL BAD_INT	; x05
	.FILL BAD_INT	; x06
	.FILL INT_KEYBOARD	; x07
	.FILL BAD_INT	; x08
	.FILL BAD_INT	; x09
	.FILL BAD_INT	; x0A
	.FILL BAD_INT	; x0B
	.FILL BAD_INT	; x0C
	.FILL BAD_INT	; x0D
	.FILL BAD_INT	; x0E
	.FILL BAD_INT	; x0F
	.FILL BAD_INT	; x10
	.FILL BAD_INT	; x11
	.FILL BAD_INT	; x12
	.FILL BAD_INT	; x13
	.FILL BAD_INT	; x14
	.FILL BAD_INT	; x15
	.FILL BAD_INT	; x16
	.FILL BAD_INT	; x17
	.FILL BAD_INT	; x18
	.FILL BAD_INT	; x19
	.FILL BAD_INT	; x1A
	.FILL BAD_INT	; x1B
	.FILL BAD_INT	; x1C
	.FILL BAD_INT	; x1D
	.FILL BAD_INT	; x1E
	.FILL BAD_INT	; x1F
	.FILL BAD_INT	; x20
	.FILL BAD_INT	; x21
	.FILL BAD_INT	; x22
	.FILL BAD_INT	; x23
	.FILL BAD_INT   ; x24
	.FILL BAD_INT	; x25
	.FILL BAD_INT	; x26
	.FILL BAD_INT	; x27
	.FILL BAD_INT	; x28
	.FILL BAD_INT	; x29
	.FILL BAD_INT	; x2A
	.FILL BAD_INT	; x2B
	.FILL BAD_INT	; x2C
	.FILL BAD_INT	; x2D
	.FILL BAD_INT	; x2E
	.FILL BAD_INT	; x2F
	.FILL BAD_INT	; x30
	.FILL BAD_INT	; x31
	.FILL BAD_INT	; x32
	.FILL BAD_INT	; x33
	.FILL BAD_INT	; x34
	.FILL BAD_INT	; x35
	.FILL BAD_INT	; x36
	.FILL BAD_INT	; x37
	.FILL BAD_INT	; x38
	.FILL BAD_INT	; x39
	.FILL BAD_INT	; x3A
	.FILL BAD_INT	; x3B
	.FILL BAD_INT	; x3C
	.FILL BAD_INT	; x3D
	.FILL BAD_INT	; x3E
	.FILL BAD_INT	; x3F
	.FILL BAD_INT	; x40
	.FILL BAD_INT	; x41
	.FILL BAD_INT	; x42
	.FILL BAD_INT	; x43
	.FILL BAD_INT	; x44
	.FILL BAD_INT	; x45
	.FILL BAD_INT	; x46
	.FILL BAD_INT	; x47
	.FILL BAD_INT	; x48
	.FILL BAD_INT	; x49
	.FILL BAD_INT	; x4A
	.FILL BAD_INT	; x4B
	.FILL BAD_INT	; x4C
	.FILL BAD_INT	; x4D
	.FILL BAD_INT	; x4E
	.FILL BAD_INT	; x4F
	.FILL BAD_INT	; x50
	.FILL BAD_INT	; x51
	.FILL BAD_INT	; x52
	.FILL BAD_INT	; x53
	.FILL BAD_INT	; x54
	.FILL BAD_INT	; x55
	.FILL BAD_INT	; x56
	.FILL BAD_INT	; x57
	.FILL BAD_INT	; x58
	.FILL BAD_INT	; x59
	.FILL BAD_INT	; x5A
	.FILL BAD_INT	; x5B
	.FILL BAD_INT	; x5C
	.FILL BAD_INT	; x5D
	.FILL BAD_INT	; x5E
	.FILL BAD_INT	; x5F
	.FILL BAD_INT	; x60
	.FILL BAD_INT	; x61
	.FILL BAD_INT	; x62
	.FILL BAD_INT	; x63
	.FILL BAD_INT	; x64
	.FILL BAD_INT	; x65
	.FILL BAD_INT	; x66
	.FILL BAD_INT	; x67
	.FILL BAD_INT	; x68
	.FILL BAD_INT	; x69
	.FILL BAD_INT	; x6A
	.FILL BAD_INT	; x6B
	.FILL BAD_INT	; x6C
	.FILL BAD_INT	; x6D
	.FILL BAD_INT	; x6E
	.FILL BAD_INT	; x6F
	.FILL BAD_INT	; x70
	.FILL BAD_INT	; x71
	.FILL BAD_INT	; x72
	.FILL BAD_INT	; x73
	.FILL BAD_INT	; x74
	.FILL BAD_INT	; x75
	.FILL BAD_INT	; x76
	.FILL BAD_INT	; x77
	.FILL BAD_INT	; x78
	.FILL BAD_INT	; x79
	.FILL BAD_INT	; x7A
	.FILL BAD_INT	; x7B
	.FILL BAD_INT	; x7C
	.FILL BAD_INT	; x7D
	.FILL BAD_INT	; x7E
	.FILL BAD_INT	; x7F
	.FILL BAD_INT	; x80
	.FILL BAD_INT	; x81
	.FILL BAD_INT	; x82
	.FILL BAD_INT	; x83
	.FILL BAD_INT	; x84
	.FILL BAD_INT	; x85
	.FILL BAD_INT	; x86
	.FILL BAD_INT	; x87
	.FILL BAD_INT	; x88
	.FILL BAD_INT	; x89
	.FILL BAD_INT	; x8A
	.FILL BAD_INT	; x8B
	.FILL BAD_INT	; x8C
	.FILL BAD_INT	; x8D
	.FILL BAD_INT	; x8E
	.FILL BAD_INT	; x8F
	.FILL BAD_INT	; x90
	.FILL BAD_INT	; x91
	.FILL BAD_INT	; x92
	.FILL BAD_INT	; x93
	.FILL BAD_INT	; x94
	.FILL BAD_INT	; x95
	.FILL BAD_INT	; x96
	.FILL BAD_INT	; x97
	.FILL BAD_INT	; x98
	.FILL BAD_INT	; x99
	.FILL BAD_INT	; x9A
	.FILL BAD_INT	; x9B
	.FILL BAD_INT	; x9C
	.FILL BAD_INT	; x9D
	.FILL BAD_INT	; x9E
	.FILL BAD_INT	; x9F
	.FILL BAD_INT	; xA0
	.FILL BAD_INT	; xA1
	.FILL BAD_INT	; xA2
	.FILL BAD_INT	; xA3
	.FILL BAD_INT	; xA4
	.FILL BAD_INT	; xA5
	.FILL BAD_INT	; xA6
	.FILL BAD_INT	; xA7
	.FILL BAD_INT	; xA8
	.FILL BAD_INT	; xA9
	.FILL BAD_INT	; xAA
	.FILL BAD_INT	; xAB
	.FILL BAD_INT	; xAC
	.FILL BAD_INT	; xAD
	.FILL BAD_INT	; xAE
	.FILL BAD_INT	; xAF
	.FILL BAD_INT	; xB0
	.FILL BAD_INT	; xB1
	.FILL BAD_INT	; xB2
	.FILL BAD_INT	; xB3
	.FILL BAD_INT	; xB4
	.FILL BAD_INT	; xB5
	.FILL BAD_INT	; xB6
	.FILL BAD_INT	; xB7
	.FILL BAD_INT	; xB8
	.FILL BAD_INT	; xB9
	.FILL BAD_INT	; xBA
	.FILL BAD_INT	; xBB
	.FILL BAD_INT	; xBC
	.FILL BAD_INT	; xBD
	.FILL BAD_INT	; xBE
	.FILL BAD_INT	; xBF
	.FILL BAD_INT	; xC0
	.FILL BAD_INT	; xC1
	.FILL BAD_INT	; xC2
	.FILL BAD_INT	; xC3
	.FILL BAD_INT	; xC4
	.FILL BAD_INT	; xC5
	.FILL BAD_INT	; xC6
	.FILL BAD_INT	; xC7
	.FILL BAD_INT	; xC8
	.FILL BAD_INT	; xC9
	.FILL BAD_INT	; xCA
	.FILL BAD_INT	; xCB
	.FILL BAD_INT	; xCC
	.FILL BAD_INT	; xCD
	.FILL BAD_INT	; xCE
	.FILL BAD_INT	; xCF
	.FILL BAD_INT	; xD0
	.FILL BAD_INT	; xD1
	.FILL BAD_INT	; xD2
	.FILL BAD_INT	; xD3
	.FILL BAD_INT	; xD4
	.FILL BAD_INT	; xD5
	.FILL BAD_INT	; xD6
	.FILL BAD_INT	; xD7
	.FILL BAD_INT	; xD8
	.FILL BAD_INT	; xD9
	.FILL BAD_INT	; xDA
	.FILL BAD_INT	; xDB
	.FILL BAD_INT	; xDC
	.FILL BAD_INT	; xDD
	.FILL BAD_INT	; xDE
	.FILL BAD_INT	; xDF
	.FILL BAD_INT	; xE0
	.FILL BAD_INT	; xE1
	.FILL BAD_INT	; xE2
	.FILL BAD_INT	; xE3
	.FILL BAD_INT	; xE4
	.FILL BAD_INT	; xE5
	.FILL BAD_INT	; xE6
	.FILL BAD_INT	; xE7
	.FILL BAD_INT	; xE8
	.FILL BAD_INT	; xE9
	.FILL BAD_INT	; xEA
	.FILL BAD_INT	; xEB
	.FILL BAD_INT	; xEC
	.FILL BAD_INT	; xED
	.FILL BAD_INT	; xEE
	.FILL BAD_INT	; xEF
	.FILL BAD_INT	; xF0
	.FILL BAD_INT	; xF1
	.FILL BAD_INT	; xF2
	.FILL BAD_INT	; xF3
	.FILL BAD_INT	; xF4
	.FILL BAD_INT	; xF5
	.FILL BAD_INT	; xF6
	.FILL BAD_INT	; xF7
	.FILL BAD_INT	; xF8
	.FILL BAD_INT	; xF9
	.FILL BAD_INT	; xFA
	.FILL BAD_INT	; xFB
	.FILL BAD_INT	; xFC
	.FILL BAD_INT	; xFD
	.FILL BAD_INT	; xFE
	.FILL BAD_INT	; xFF

;;; OS_START - operating system entry point (always starts at x0200)
OS_START
	;; Set the Memory Protection Register (MPR)
	LD  R0, MPR_INIT
	STI R0, OS_MPR

	;; set timer interval
	LD  R0, TIM_INIT
	STI R0, OS_TMI

	;; start running user code (clear Privilege bit w/ JMPT)
	LD R7, USER_CODE_ADDR
	JMPT R7

;; Register locations
OS_KBSR	.FILL xFE00		; keyboard status register
OS_KBDR	.FILL xFE02		; keyboard data register
OS_DSR	.FILL xFE04		; display status register
OS_DDR	.FILL xFE06		; display data register
OS_TMR	.FILL xFE08		; timer register
OS_TMI  .FILL xFE0A     ; timer interval register
OS_MPR	.FILL xFE12		; memory protection register
OS_MCR	.FILL xFFFE		; machine control register

;; Local register storage
OS_SAVE_R0      .BLKW 1
OS_SAVE_R1      .BLKW 1
OS_SAVE_R2      .BLKW 1
OS_SAVE_R3      .BLKW 1
OS_SAVE_R4      .BLKW 1
OS_SAVE_R5      .BLKW 1
OS_SAVE_R6      .BLKW 1
OS_SAVE_R7      .BLKW 1
OS_OUT_SAVE_R1  .BLKW 1
OS_IN_SAVE_R7   .BLKW 1
                	
MASK_HI         .FILL x7FFF
HIGH_BIT        .FILL x8000
LOW_8_BITS      .FILL x00FF
TIM_INIT        .FILL #40
;MPR_INIT	.FILL xFFFF	; user can access everything
MPR_INIT	    .FILL x0FF8	; user can access x3000 to xbfff
USER_CODE_ADDR	.FILL x3000	; user code starts at x3000

;;; GETC - Read a single character of input from keyboard device into R0
TRAP_GETC
	LDI R0,OS_KBSR		; wait for a keystroke
	BRzp TRAP_GETC

	;; DMC change: clear the Ready Bit of the Keyboard Status Register
	ST  R1, OS_SAVE_R1
	LD  R1, MASK_HI
	AND R0, R0, R1
	STI R0, OS_KBSR
	LD  R1, OS_SAVE_R1

	LDI R0,OS_KBDR		; Read in the character

	;; DMC this should be RTT to restore the privilege level
	RTT
        
;;; OUT - Write the character in R0 to the console.
TRAP_OUT
	ST R1,OS_OUT_SAVE_R1	; save R1
TRAP_OUT_WAIT
	LDI R1,OS_DSR		    ; wait for the display to be ready
	BRzp TRAP_OUT_WAIT
	STI R0,OS_DDR		    ; write the character and return
	LD R1,OS_OUT_SAVE_R1	; restore R1

	;; DMC this should be RTT to restore the privilege level
	RTT

;;; CLEAR_CONSOLE- clear the console
TRAP_CLEAR_CONSOLE
    ST R1,  OS_OUT_SAVE_R1	; save R1
    LD R1, HIGH_BIT
    STI R1, OS_DDR		    ; write the character and return
    LD R1,OS_OUT_SAVE_R1	; restore R1

    ;; DMC this should be RTT to restore the privilege level
    RTT

                
;;; PUTS - Write a NUL-terminated string of characters to the console,
;;; starting from the address in R0.	
TRAP_PUTS
	ST R0,OS_SAVE_R0	; save R0, R1, and R7
	ST R1,OS_SAVE_R1
	ST R7,OS_SAVE_R7
	ADD R1,R0,#0		; move string pointer (R0) into R1

TRAP_PUTS_LOOP
	LDR R0,R1,#0		; write characters in string using OUT
	BRz TRAP_PUTS_DONE
	OUT
	ADD R1,R1,#1
	BRnzp TRAP_PUTS_LOOP

TRAP_PUTS_DONE
	LD R0,OS_SAVE_R0	; restore R0, R1, and R7
	LD R1,OS_SAVE_R1
	LD R7,OS_SAVE_R7
	;; DMC this should be RTT to restore the privilege level to 'USER'
	RTT

;;; IN - prompt the user for a single character input, which is stored
;;; in R0 and also echoed to the console.        
TRAP_IN
	ST R7,OS_IN_SAVE_R7	; save R7 (no need to save R0, since we 
				        ;    overwrite later
	LEA R0,TRAP_IN_MSG	; prompt for input
	PUTS
	GETC			    ; read a character
	OUT			        ; echo back to monitor
	ST R0,OS_SAVE_R0	; save the character
	AND R0,R0,#0		; write a linefeed, too
	ADD R0,R0,#10
	OUT
	LD R0,OS_SAVE_R0	; restore the character
	LD R7,OS_IN_SAVE_R7	; restore R7
	RTT                     ; this doesn't work, because


;;; PUTSP - Write a NUL-terminated string of characters, packed 2 per
;;; memory location, to the console, starting from the address in R0.
TRAP_PUTSP
	; NOTE: This trap will end when it sees any NUL, even in
	; packed form, despite the P&P second edition's requirement
	; of a double NUL.

	ST R0,OS_SAVE_R0	; save R0, R1, R2, R3, and R7
	ST R1,OS_SAVE_R1
	ST R2,OS_SAVE_R2
	ST R3,OS_SAVE_R3
	ST R7,OS_SAVE_R7
	ADD R1,R0,#0		; move string pointer (R0) into R1

TRAP_PUTSP_LOOP
	LDR R2,R1,#0		; read the next two characters
	LD R0,LOW_8_BITS	; use mask to get low byte
	AND R0,R0,R2		; if low byte is NUL, quit printing
	BRz TRAP_PUTSP_DONE
	OUT			; otherwise print the low byte

	AND R0,R0,#0		; shift high byte into R0
	ADD R3,R0,#8
TRAP_PUTSP_S_LOOP
	ADD R0,R0,R0		; shift R0 left
	ADD R2,R2,#0		; move MSB from R2 into R0
	BRzp TRAP_PUTSP_MSB_0
	ADD R0,R0,#1
TRAP_PUTSP_MSB_0
	ADD R2,R2,R2		; shift R2 left
	ADD R3,R3,#-1
	BRp TRAP_PUTSP_S_LOOP

	ADD R0,R0,#0		; if high byte is NUL, quit printing
	BRz TRAP_PUTSP_DONE
	OUT			; otherwise print the low byte

	ADD R1,R1,#1		; and keep going
	BRnzp TRAP_PUTSP_LOOP

TRAP_PUTSP_DONE
	LD R0,OS_SAVE_R0	; restore R0, R1, R2, R3, and R7
	LD R1,OS_SAVE_R1
	LD R2,OS_SAVE_R2
	LD R3,OS_SAVE_R3
	LD R7,OS_SAVE_R7
	;;  DMC this should be RTT to restore the privilege level to 'USER'
	RTT


;;; HALT - trap handler for halting machine
TRAP_HALT	
	LDI R0,OS_MCR		
	LD R1,MASK_HI       ; clear run bit in MCR
	AND R0,R0,R1
	STI R0,OS_MCR		; halt!
	BRnzp OS_START		; restart machine

        
;;; BAD_TRAP - code to execute for undefined trap
BAD_TRAP
	BRnzp TRAP_HALT		; execute HALT


;;; BAD_INT - code to execute for undefined interrupt. There won't 
;;; actually be any interrupts, so this will never actually get called.
BAD_INT		RTI

TRAP_IN_MSG	.STRINGZ "\nInput a character> "

;;  These are the parameters of the video display
VIDEO_COLS	    .FILL	128	; Number of pixels per row
VIDEO_ROWS 	    .FILL	124	; Number of rows
;
; Draw a rect on the WCSUSim monitor
;
;
; Register usage
;
;	R0	Starting row number
; 	R1	Starting column number
;	R2	Width in pixels
;	R3	Height in pixels
;	R4	Color
;	R5	temp
;	R6	temp
;
TRAP_DRAW_RECT
	;; Save the values in registers 0 - 3 and 5 and 6
	ST R0, VIDEO_SAVE_R0
	ST R1, VIDEO_SAVE_R1
	ST R2, VIDEO_SAVE_R2
	ST R3, VIDEO_SAVE_R3
	ST R5, VIDEO_SAVE_R5
	ST R6, VIDEO_SAVE_R6
	
	;; Sanity check: Make sure that the values in R2 and R3 are > 0
	CMPi	  R2, #0
	BRnz	  TRAP_DRAW_RECT_END
	CMPi	  R3, #0
	BRnz	  TRAP_DRAW_RECT_END

	; Convert the value in R3 to be the ending row
	; Compare this new value and the maximum number of rows
	ADD	  R3, R3, R0
	LD	  R5, VIDEO_ROWS	
	CMP	  R3, R5		
	BRnz TRAP_DRAW_RECT_FIRST_ROW
	LD	  R3, VIDEO_ROWS

TRAP_DRAW_RECT_FIRST_ROW
	;; Similarly, convert the value in R0 to be the first row
	CMPi	  R0, #0
	BRzp	  TRAP_DRAW_RECT_LAST_COLUMN
	CLR	      R0

TRAP_DRAW_RECT_LAST_COLUMN
	;; Convert the value in R2 to be the last column
	ADD	   R2, R2, R1
	LDI	   R5, VIDEO_COLS
	CMP	   R2, R5
	BRnz   TRAP_DRAW_RECT_FIRST_COLUMN
	LDI	   R2, VIDEO_COLS

TRAP_DRAW_RECT_FIRST_COLUMN
	;; Make sure the first column is not less than zero
	CMPi 	   R1, #0
	BRzp	   TRAP_DRAW_RECT_BEGIN_LOOP
	CLR        R1

TRAP_DRAW_RECT_BEGIN_LOOP
	;; Registers used here
	;;    R0    row
	;;    R5    current column
	;;    R6    pointer to pixel
    BR TRAP_DRAW_RECT_CHECK_ROW
TRAP_DRAW_RECT_OUTER_LOOP

    ;; Compute the pointer to the video memory
    ;; address = 0xC000 + row * 128 + column
    SHFli	 R6, R0, #7 ; R6 = row * 128
    LD	 R5, VIDEO_MEM
    ADD	 R6, R6, R5	; R6 = &VIDEO_ME + row * 128
    ADD	 R6, R6, R1

    ; Put the starting column into R5
    ADD   	R5, R1, #0
    BR	TRAP_DRAW_RECT_CHECK_COL

TRAP_DRAW_RECT_INNER_LOOP
	STR	R4, R6, #0	; Store the color in the pixel
    ADD	R6, R6, #1	; Increment pixel
    ADD	R5, R5, #1	; Increment column

TRAP_DRAW_RECT_CHECK_COL
	CMP	R5, R2
	BRn	TRAP_DRAW_RECT_INNER_LOOP

	; Increment the row number
	ADD R0, R0, #1

TRAP_DRAW_RECT_CHECK_ROW
	CMP	R0, R3
	BRn	TRAP_DRAW_RECT_OUTER_LOOP

TRAP_DRAW_RECT_END
	;; Restore the saved registers
    LD R0, VIDEO_SAVE_R0
    LD R1, VIDEO_SAVE_R1
    LD R2, VIDEO_SAVE_R2
    LD R3, VIDEO_SAVE_R3
	LD R5, VIDEO_SAVE_R5
	LD R6, VIDEO_SAVE_R6
	RTT

; Declare these here since the earlier versions are now unreachable
VIDEO_SAVE_R0 .BLKW 1
VIDEO_SAVE_R1 .BLKW 1
VIDEO_SAVE_R2 .BLKW 1
VIDEO_SAVE_R3 .BLKW 1
VIDEO_SAVE_R4 .BLKW 1
VIDEO_SAVE_R5 .BLKW 1
VIDEO_SAVE_R6 .BLKW 1
VIDEO_SAVE_R7 .BLKW 1

;; Constants for the Video Monitor
VIDEO_MEM	.FILL	0xC000

;; Draw a line using Bresenham's algorithm
;; Register usage
;;    R0   x0
;;    R1   y0
;;    R2   x1
;;    R3   y1
;;    R4   color
;;    R5 temp
;;    R6 temp
;;    R7 temp

TRAP_DRAW_LINE
    ;; Save the temporary registers
    ST R5, VIDEO_SAVE_R5
    ST R6, VIDEO_SAVE_R6
    ST R7, VIDEO_SAVE_R7

    ;; Decide which octant we are in
    ;;    if( abs(y1 - y0 ) < abs(x1 - x0) )
    SUB R5, R3, R1
    CMPi R5, #0
    BRzp TRAP_DRAW_LINE_ABS_Y
    ;; If y1 - y0 is negative, flip the sign
    NOT R5, R5
    ADD R5, R5, #1
TRAP_DRAW_LINE_ABS_Y
    SUB R6, R2, R0
    CMPi R6, #0
    BRzp TRAP_DRAW_LINE_CMP_ABS
    ;; If x1 - x0 is negative, flip the sign
    NOT R6, R6
    ADD R6, R6, #1
TRAP_DRAW_LINE_CMP_ABS
    CMP R5, R6
    BRzp TRAP_DRAW_LINE_HIGH_SLOPE_CASE

    ;; We are in the low slope case -1 < slope < 1: x will be incremented by one each iteration.
    ;; Decide if the line goes from right to left, or left to right
    ;; Save the registers first
    ST R0, VIDEO_SAVE_R0
    ST R1, VIDEO_SAVE_R1
    ST R2, VIDEO_SAVE_R2
    ST R3, VIDEO_SAVE_R3

    ;; Now compare X0 and X1 to find the direction of the line.
    CMP R0, R2
    BRnz TRAP_DRAW_LINE_LOW_SLOPE_LEFT_TO_RIGHT
    ;; If we get here, X0 is greater than X1, so
    ;; we need to swap x0 <--> x1 and y0 <--> y1
    ;;     X0, X1
    CPR R5, R0
    CPR R0, R2
    CPR R2, R5

    ;;      Y0, Y1
    CPR R5, R1
    CPR R1, R3
    CPR R3, R5

    ;; Draw the actual line
TRAP_DRAW_LINE_LOW_SLOPE_LEFT_TO_RIGHT
    SUB R5, R2, R0  ; dx
    SUB R6, R3, R1  ; dy
    LC  R7, #1      ; Yi = 1

    ;; Check if the line goes upwards, or downwards
    CMPi R6, #0
    BRzp TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS

    ;; If it slopes down, we need to invert everything so it seems to go upwards
    LC R7, #-1      ; Yi = -1
    NOT R6, R6      ; R6 --> -R6
    ADD R6, R6, #1

TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS
    ;; Setup
    SHFli R3, R6, #1                    ; D = 2*dy - dx
    SUB   R3, R3, R5

    ;; Now we loop, finally!
TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS_LOOP_TOP
    ;; for x from x0 to x1
    CMP R0, R2
    BRzp TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS_LOOP_DONE

    ;; Plot the current pixel
    ;;    Save R5 and R6
    ST R5, TRAP_DRAW_LINE_DX
    ST R6, TRAP_DRAW_LINE_DY

    ;; Compute the memory location corresponding to x and y
    SHFli R6, R1, #7    ; R6 = y* 128
    LD	  R5, VIDEO_MEM
    ADD	  R6, R6, R5	; R6 = &VIDEO_ME + row * 128
    ADD	  R6, R6, R0    ; R6 = &VIDEO_MEM + y*128 + x

    STR	R4, R6, #0	; Store the color in the pixel

    ;; Restore dx and dy
    LD R5, TRAP_DRAW_LINE_DX
    LD R6, TRAP_DRAW_LINE_DY

    ;; See if y needs updating
    CMPi R3, #0 ; if( D > 0 )
    BRnz TRAP_DRAW_LINE_LOW_SLOPE_UPDATE_D
    ADD R1, R1, R7 ; y += yi
    SUB R3, R3, R5 ; D -= 2*dx
    SUB R3, R3, R5

TRAP_DRAW_LINE_LOW_SLOPE_UPDATE_D
    ADD R3, R3, R6 ; D += 2*dy
    ADD R3, R3, R6

    ;; Also increment x
    ADD R0, R0, #1
    BR TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS_LOOP_TOP

TRAP_DRAW_LINE_LOW_SLOPE_UPWARDS_LOOP_DONE
    ;; Restore the registers we used in the loop
    LD R0, VIDEO_SAVE_R0
    LD R1, VIDEO_SAVE_R1
    LD R2, VIDEO_SAVE_R2
    LD R3, VIDEO_SAVE_R3
    BR TRAP_DRAW_LINE_END

TRAP_DRAW_LINE_HIGH_SLOPE_CASE
    ;; We are in the High slope case: slope < -1 or slope > 1: y will be incremented by one each iteration.
    ;; Decide if the line goes from top to bottom, or bottom to top
    ;; Save the registers first
    ST R0, VIDEO_SAVE_R0
    ST R1, VIDEO_SAVE_R1
    ST R2, VIDEO_SAVE_R2
    ST R3, VIDEO_SAVE_R3

    ;; Now compare Y0 and Y1 to find the direction of the line.
    CMP R1, R3
    BRnz TRAP_DRAW_LINE_HIGH_SLOPE_TOP_TO_BOTTOM
    ;; If we get here, Y0 is greater than Y1, so
    ;; we need to swap x0 <--> x1 and y0 <--> y1
    ;;     X0, X1
    CPR R5, R0
    CPR R0, R2
    CPR R2, R5

    ;;      Y0, Y1
    CPR R5, R1
    CPR R1, R3
    CPR R3, R5

    ;; Draw the actual line
TRAP_DRAW_LINE_HIGH_SLOPE_TOP_TO_BOTTOM
    SUB R5, R2, R0  ; dx
    SUB R6, R3, R1  ; dy
    LC  R7, #1      ; Xi = 1

    ;; Check if the line goes to the left or to the right
    CMPi R5, #0
    BRzp TRAP_DRAW_LINE_LOW_SLOPE_RIGHTWARDS

    ;; If it slopes to the left, we need to invert everything so it seems to go to the right
    LC R7, #-1      ; Xi = -1
    NOT R5, R5      ; R5 --> -R5  ( dx --> -dx )
    ADD R5, R5, #1

TRAP_DRAW_LINE_LOW_SLOPE_RIGHTWARDS
    ;; Setup
    CPR   R2, R3      ; R2 = Y1, crunch X1
    SHFli R3, R5, #1  ; D = 2*dx - dy
    SUB   R3, R3, R6

    ;; Now we loop, finally!
TRAP_DRAW_LINE_HIGH_SLOPE_RIGHTWARDS_LOOP_TOP
    ;; for y from y0 to y1
    CMP R1, R2
    BRzp TRAP_DRAW_LINE_HIGH_SLOPE_RIGHTWARDS_LOOP_DONE

    ;; Plot the current pixel
    ;;    Save R5 and R6
    ST R5, TRAP_DRAW_LINE_DX
    ST R6, TRAP_DRAW_LINE_DY

    ;; Compute the memory location corresponding to x and y
    SHFli R6, R1, #7    ; R6 = y* 128
    LD	  R5, VIDEO_MEM
    ADD	  R6, R6, R5	; R6 = &VIDEO_ME + row * 128
    ADD	  R6, R6, R0    ; R6 = &VIDEO_MEM + y*128 + x

    STR	R4, R6, #0	; Store the color in the pixel

    ;; Restore dx and dy
    LD R5, TRAP_DRAW_LINE_DX
    LD R6, TRAP_DRAW_LINE_DY

    ;; See if x needs updating
    CMPi R3, #0 ; if( D > 0 )
    BRnz TRAP_DRAW_LINE_HIGH_SLOPE_UPDATE_D
    ADD R0, R0, R7 ; x += yi
    SUB R3, R3, R6 ; D -= 2*dy
    SUB R3, R3, R6

TRAP_DRAW_LINE_HIGH_SLOPE_UPDATE_D
    ADD R3, R3, R5 ; D += 2*dx
    ADD R3, R3, R5

    ;; Also increment y
    ADD R1, R1, #1
    BR TRAP_DRAW_LINE_HIGH_SLOPE_RIGHTWARDS_LOOP_TOP

TRAP_DRAW_LINE_HIGH_SLOPE_RIGHTWARDS_LOOP_DONE
    ;; Restore the registers we used in the loop
    LD R0, VIDEO_SAVE_R0
    LD R1, VIDEO_SAVE_R1
    LD R2, VIDEO_SAVE_R2
    LD R3, VIDEO_SAVE_R3
    BR TRAP_DRAW_LINE_END


TRAP_DRAW_LINE_END
    ;; Restore the temporary registers
    LD R5, VIDEO_SAVE_R5
    LD R6, VIDEO_SAVE_R6
    LD R7, VIDEO_SAVE_R7
    RTT

;; Local variables
TRAP_DRAW_LINE_DX .BLKW   1
TRAP_DRAW_LINE_DY .BLKW   1

;; Interrupt handling
INT_SAVE_R0 .BLKW 1
INT_SAVE_R1 .BLKW 1
INT_SAVE_R2 .BLKW 1

;; Interrupt vectors
KEYBOARD_INT_VECTOR   .FILL x0007
INT_MCR	              .FILL xFFFE		; machine control register
ENABLE_INTERRUPTS_BIT .FILL x4000
INT_MASK_HIGH_BIT     .FILL x7FFF

;; Copy of the register definitions
INT_OS_KBSR	.FILL xFE00		; keyboard status register
INT_OS_KBDR	.FILL xFE02		; keyboard data register

;; These are the locations of the user's ISRs
INT_KEYPRESS_ISR .FILL x0000

;; Enable interrupts
TRAP_ENABLE_INTERRUPTS
    ;; Save registers
    ST R0, INT_SAVE_R0
    ST R1, INT_SAVE_R1

    ;; Get the current value of the Machine Control Register
    LDI R0, INT_MCR

    ;; Get the 'Enable Interrupts' bit
    LD R1, ENABLE_INTERRUPTS_BIT

    ;; Set the Interrupt Enable bit
    OR R0, R0, R1

    ;; Save the new value away
    STI R0, INT_MCR

    ;; Restore the registers
    LD R0, INT_SAVE_R0
    LD R1, INT_SAVE_R1

    ;; That's it
    RTT

;; Disable interrupts
TRAP_DISABLE_INTERRUPTS
    ;; Save registers
    ST R0, INT_SAVE_R0
    ST R1, INT_SAVE_R1

    ;; Get the current value of the Machine Control Register
    LDI R0, INT_MCR

    ;; Get the 'Enable Interrupts' bit
    LD R1, ENABLE_INTERRUPTS_BIT

    ;; Clear the Interrupt Enable bit
    NOT R1, R1
    AND R0, R0, R1

    ;; Save the new value away
    STI R0, INT_MCR

    ;; Restore the registers
    LD R0, INT_SAVE_R0
    LD R1, INT_SAVE_R1

    ;; That's it
    RTT

;; Attach a user ISR
;;    Register usage
;;    R0   Interrupt Vector
;;    R1   Address of ISR
TRAP_ATTACH_ISR
    ;; Check the interrupt vector
    ST R2, INT_SAVE_R2
    LD R2, KEYBOARD_INT_VECTOR

    CMP R0, R2
    BRnp TRAP_ATTACH_ISR_UNKNOWN_VECTOR

    ST R1, INT_KEYPRESS_ISR

TRAP_ATTACH_ISR_UNKNOWN_VECTOR
    LD R2, INT_SAVE_R2

    ;; That's it
    RTT

;; Detach a previously attached user ISR
;;    Register usage
;;    R0   Interrupt Vector
TRAP_DETACH_ISR
    ;; Check the interrupt vector
    ST R2, INT_SAVE_R2
    LD R2, KEYBOARD_INT_VECTOR

    CMP R0, R2
    BRnp TRAP_DETACH_ISR_UNKNOWN_VECTOR

    CLR R2
    ST R2, INT_KEYPRESS_ISR

TRAP_DETACH_ISR_UNKNOWN_VECTOR
    LD R2, INT_SAVE_R2

    ;; That's it
    RTT

;; Interrupt handlers
INT_KEYBOARD
    ;; Save register 0
    ST R0, INT_SAVE_R0

    ;; Copy the value in the Keyboard Data Register to R0
    LDI R0, INT_OS_KBDR

    ;; Get the address of the user's ISR
    ST   R1, INT_SAVE_R1
    LD   R1, INT_KEYPRESS_ISR
    CMPi R1, #0
    BRnz INT_KEYBOARD_RESTORE_REGISTERS

    ;; Go execute the ISR
    JMPISR R1

    ;; Clear the "Ready Bit" in the Keyboard Status Register
    ST  R2, INT_SAVE_R2
    LDI R1, INT_OS_KBSR
    LD  R2, INT_MASK_HIGH_BIT
    AND R1, R1, R2
    LD  R2, INT_SAVE_R2
    STI R1, INT_OS_KBSR

INT_KEYBOARD_RESTORE_REGISTERS
    ;; All done!
    LD R0, INT_SAVE_R0
    LD R1, INT_SAVE_R1
    RTI

;; Disk Support constants
DISK_BASE_ADDRESS     .FILL  x7000
DISK_READ_COMMAND     .FILL  x0001
DISK_WRITE_COMMAND    .FILL  x0002
DISK_CLEAR_STATUS_BIT .FILL  x7FFF
DISK_SET_STATUS_BIT   .FILL  x8000
DISK_WORDS_PER_INODE  .FILL  #32
DISK_BLOCK_SIZE       .FILL  #4096

FS_SUCCESS            .FILL  x0001
pDISK_INODE_MAP                 .FILL  x1000
pDISK_DATABLOCK_MAP             .BLKW  1
pDISK_FIRST_INODE               .BLKW  1
pDISK_FREE_SPACE                .BLKW  1
pDISK_FIRST_DIRECTORY_DATABLOCK .FILL x2000

;; Disk register assignments
OS_DDSR .FILL xFE14     ; Disk Status Register
OS_DDCR .FILL xFE16     ; Disk Control Register
OS_DDBR .FILL xFE18     ; Disk Block Register
OS_DDMR .FILL xFE1A     ; Disk Memory Register

;; Register storage area
DISK_SAVE_R0 .BLKW 1
DISK_SAVE_R1 .BLKW 1
DISK_SAVE_R2 .BLKW 1
DISK_SAVE_R3 .BLKW 1
DISK_SAVE_R4 .BLKW 1
DISK_SAVE_R5 .BLKW 1

;; Super Block Parameters
NUMBER_OF_INODES           .BLKW 1
NUMBER_OF_DATABLOCKS       .BLKW 1
ADDRESS_OF_INODE_MAP       .BLKW 1
ADDRESS_OF_DATABLOCK_MAP   .BLKW 1
ADDRESS_OF_FIRST_INODE     .BLKW 1
ADDRESS_OF_FIRST_DATABLOCK .BLKW 1
TOTAL_BLOCKS               .BLKW 1

;; Read the disk's super block
TRAP_MOUNT_DISK
    ;; Save the value in R1
    ST  R1, DISK_SAVE_R1
    ST  R2, DISK_SAVE_R2
    ST  R3, DISK_SAVE_R3

    ;; Load the block address, 0 in this case for the Super Block and ...
    CLR R0

    ;; ... read it in.
    FS_RAW_READ

    ;; Fetch the parameters
    LD  R0, DISK_BASE_ADDRESS
    ADD R0, R0, #7    ;; Skip over the 'magic' and first 3 bytes of the number of INodes

    ;;     Number of INodes
    LDR R1, R0, #0
    ST  R1, NUMBER_OF_INODES
    ADD R0, R0, #4

    ;;     Number of data blocks
    LDR R1, R0, #0
    ST  R1, NUMBER_OF_DATABLOCKS
    ADD R0, R0, #4

    ;;     Address of INode map
    LDR R1, R0, #0
    ST  R1, ADDRESS_OF_INODE_MAP
    ADD R0, R0, #4

    ;;     Address of Data block map
    LDR R1, R0, #0
    ST  R1, ADDRESS_OF_DATABLOCK_MAP
    ADD R0, R0, #4

    ;;     Address of first INode
    LDR R1, R0, #0
    ST  R1, ADDRESS_OF_FIRST_INODE
    ADD R0, R0, #4

    ;;     Address of first Data block
    LDR R1, R0, #0
    ST  R1, ADDRESS_OF_FIRST_DATABLOCK
    ADD R0, R0, #4

    ;;     Total number of blocks
    LDR R1, R0, #0
    ST  R1, TOTAL_BLOCKS

    ;; Get the INode map
    ;; Load the block address and ...
    LD R0, ADDRESS_OF_INODE_MAP

    ;; ... read it in
    FS_RAW_READ

    ;; Compute the number of words needed
    LD R0, NUMBER_OF_INODES
    LC R1, #8
    DIV R0, R0, R1

    ;; This are the destination and source of the INode Map
    LD R1, pDISK_INODE_MAP
    LD R2, DISK_BASE_ADDRESS

    FS_COPY_WORDS

    ;; Similarly get the Datablock map
    ST R1, pDISK_DATABLOCK_MAP

    ;; Load the block address of the Datablock map and ...
    LD R0, ADDRESS_OF_DATABLOCK_MAP

    ;; ... read it in
    FS_RAW_READ

    ;; Compute the number of words needed: Round up to the nearest multiple of 8
    LD R0, NUMBER_OF_DATABLOCKS
    ADD R0, R0, #7
    LC R1, #8
    DIV R0, R0, R1

    ;; This are the destination and source of the Datablock Map
    LD R1, pDISK_DATABLOCK_MAP
    LD R2, DISK_BASE_ADDRESS

    FS_COPY_WORDS

    ;; Now read in the first INode
    ST R1, pDISK_FIRST_INODE

    ;; Load the block address of the first INode and ...
    LD R0, ADDRESS_OF_FIRST_INODE

    ;; ... read it in
    FS_RAW_READ

    ;; Load the number of words needed:
    LD R0, DISK_WORDS_PER_INODE

    ;; This are the destination and source of the first INode
    LD R1, pDISK_FIRST_INODE
    LD R2, DISK_BASE_ADDRESS

    FS_COPY_WORDS

    ;; The root INode will always have at least one directory data block allocated
    ;; If nothing else, it will contain a link to "."
    ST R1, pDISK_FREE_SPACE

    ;; Get the first direct pointer and ...
    LD  R0, pDISK_FIRST_INODE
    LDR R1, R0, #14   ;; Second to last byte
    LC  R2, #8
    SHFL R1, R1, R2   ;; R1 now contains the high byte
    LDR R2, R0, #15   ;; Last byte
    OR  R0, R1, R2

    ;; ... read in that block
    FS_RAW_READ

    ;; This is a full block
    LD R0, DISK_BLOCK_SIZE

    ;; This are the destination and source of the first Directory Datablock
    LD R1, pDISK_FIRST_DIRECTORY_DATABLOCK
    LD R2, DISK_BASE_ADDRESS

    FS_COPY_WORDS

    ;; Restore the registers
FS_MOUNT_DONE
    LD R1, DISK_SAVE_R1
    LD R2, DISK_SAVE_R2
    LD R3, DISK_SAVE_R3

    ;; Set the success code
    LD R0, FS_SUCCESS
    RTT

;; Read one block from the external disk and place the contents into
;; the first page of file system storage
;;     Register usage:
;;          On entry, R0 contains the block number to be read
TRAP_RAW_READ
    ST R0, DISK_SAVE_R0
    ST R1, DISK_SAVE_R1

    ;; Load the block address into the block register
    STI R0, OS_DDBR

    ;; Load the READ command in the control register
    LD R0, DISK_READ_COMMAND
    STI R0, OS_DDCR

    ;; Load the address of the first page of FS storage into the memory register
    LD R0, DISK_BASE_ADDRESS
    STI R0, OS_DDMR

    ;; Clear the READY bit in the status register so the read begins
    LDI R0, OS_DDSR
    LD R1, DISK_CLEAR_STATUS_BIT
    AND R0, R0, R1
    STI R0, OS_DDSR

    ;; Now wait for the READY bit to be set again.
    ;; This indicates that the READ is finished
    LD R1, DISK_SET_STATUS_BIT
FS_RAWREAD_WAIT_FOR_STATUS_LOOP
    LDI R0, OS_DDSR
    AND R0, R0, R1
    BRz FS_RAWREAD_WAIT_FOR_STATUS_LOOP

    ;; We're done. Restore the registers
    LD R0, DISK_SAVE_R0
    LD R1, DISK_SAVE_R1

    ;; That's it!
    RTT

;; Copy a number of words from a source location to a destination location
;;   Register usage
;;   On Entry
;;      R0: number of words to copy  (unchanged)
;;      R1: destination address (changed)
;;      R2: source address (unchanged)
;;   On Exit
;;      R1: next destination address
;;   Temporary
;;      R3
TRAP_COPY_WORDS
    ST R0, DISK_SAVE_R0
    ST R2, DISK_SAVE_R2
    ST R3, DISK_SAVE_R3

    ;; Copy R0 to itself so that the CC register is set appropriately
    CPR R0, R0

TRAP_COPY_WORDS_LOOP
    BRz TRAP_COPY_WORDS_DONE
    LDR R3, R2, #0    ;; Get the source word and ...
    STR R3, R1, #0    ;; ... store it at the destination address
    ADD R1, R1, #1
    ADD R2, R2, #1
    ADD R0, R0, #-1
    BR TRAP_COPY_WORDS_LOOP

TRAP_COPY_WORDS_DONE
    ;; Restore the registers
    LD R0, DISK_SAVE_R0
    LD R2, DISK_SAVE_R2
    LD R3, DISK_SAVE_R3

    ;; That's it
    RTT


pReadFilePath .BLKW 1
READ_SAVE_R1  .BLKW 1
READ_SAVE_R2  .BLKW 1
READ_SAVE_R3  .BLKW 1
READ_SAVE_R4  .BLKW 1
READ_SAVE_R5  .BLKW 1
;; Open a file in the attached, mounted file system
;;   Register usage:
;;      On Entry
;;        R0:  a zero terminated string containing the path to the file
;;      On exit
;;        R0:  the INode corresponding to this file

TRAP_OPEN_READ_FILE
    ;;Save the file path
    ST R0, pReadFilePath
    ST R1, READ_SAVE_R1
    ST R2, READ_SAVE_R2
    ST R3, READ_SAVE_R3
    ST R4, READ_SAVE_R4
    ST R5, READ_SAVE_R5

    ;; Compute the length of the file path
    FS_STRING_LENGTH

    ;; Now use this to compute the address of the end of the string
    CPR R4, R0
    LD R0, pReadFilePath
    ADD R4, R4, R0
    CPR R1, R0

    ;; Now R1 contains the address of the beginning of the string
    ;; and R4 contains the address of the end
    ;; Look up the INode corresponding to this file
    CLR R3 ;; Start initially at the Root Inode, namely 0
FS_OPEN_READ_FILE_COMPONENT_LOOP
    ;; See if we are done
    CMP R1, R4
    BRzp FS_OPEN_READ_FILE_DONE

    ;; The file path must begin with a '/'. Skip over it.
    ADD R1, R1, #1

    ;; Now see if there is another slash
    CPR R2, R1
FS_OPEN_READ_FILE_PARSE_LOOP
    ;; Check if R2 points to a '/' character
    LDR R5, R2, #0
    CMPi R5, x2F ;; The '/' character
    BRz FS_OPEN_READ_FILE_PARSE_DONE
    ;; Check if R2 points to the end of the string
    CMP R2, R4
    BRz FS_OPEN_READ_FILE_PARSE_DONE

    ;; Otherwise, advance to the next character
    ADD  R2, R2, #1
    BR FS_OPEN_READ_FILE_PARSE_LOOP

FS_OPEN_READ_FILE_PARSE_DONE
    FS_FIND_INODE
    ;; Copy R2 to R1 to continue
    CPR R1, R2
    BR FS_OPEN_READ_FILE_COMPONENT_LOOP

FS_OPEN_READ_FILE_DONE
    ;; Save the INode
    CPR R0, R3

    ;; Restore the registers
    LD R1, READ_SAVE_R1
    LD R2, READ_SAVE_R2
    LD R3, READ_SAVE_R3
    LD R4, READ_SAVE_R4
    LD R5, READ_SAVE_R5

    ;; That's it
    RTT

FIND_INODE_SAVE_R0 .BLKW 1
FIND_INODE_SAVE_R1 .BLKW 1
FIND_INODE_SAVE_R2 .BLKW 1
FIND_INODE_SAVE_R4 .BLKW 1
FIND_INODE_SAVE_R5 .BLKW 1
FIND_INODE_RECORDLEN .BLKW 1
FIRST_INODE_BLOCK  .FILL 3
FS_INODES_PER_BLOCK           .FILL  #128
FS_WORDS_PER_INODE            .FILL  #32
FS_FREE_SPACE                 .FILL  x1038  ;; Root INode is stored at x1018
FS_FIRST_DIRECTORY_DATA_BLOCK .FILL x2000
FS_BASE_ADDRESS               .FILL x7000
FS_BLOCK_SIZE                 .FILL #4096

;; Find the INode corresponding to a given file
;;   Register usage:
;;      On Entry
;;        R0:  a zero terminated string containing the path to a file
;;        R1:  the position of the starting character of the path component
;;        R2:  the position of the ending character of the path component
;;        R3:  the INode containing the file component
;;      On exit
;;        R3:  the INode corresponding to this path component
TRAP_FIND_INODE
    ST R0, FIND_INODE_SAVE_R0
    ST R1, FIND_INODE_SAVE_R1
    ST R2, FIND_INODE_SAVE_R2
    ST R4, FIND_INODE_SAVE_R4
    ST R5, FIND_INODE_SAVE_R5

    ;; Load the INode
    ;; Load the block address of the first INode and ...
    LD R0, FIRST_INODE_BLOCK

    ;; ... update it with the the number of the desired INode
    LD  R4, FS_INODES_PER_BLOCK
    DIV R5, R3, R4
    ADD R0, R0, R5

    ;; ... read it in
    FS_RAW_READ

    ;; Load the number of words needed:
    LD R0, FS_WORDS_PER_INODE

    ;; This are the destination and source of the desired INode
    LD R1, FS_FREE_SPACE
    LD R2, FS_BASE_ADDRESS
    CPR R4, R3
    MUL R4, R4, R0  ;; R4 now contains the offset to the desired INode
    ADD R2, R2, R4

    FS_COPY_WORDS

    ;; Get the first direct pointer and ...
    LD  R0, FS_FREE_SPACE
    LDR R1, R0, #14   ;; Second to last byte
    LC  R2, #8
    SHFL R1, R1, R2   ;; R1 now contains the high byte
    LDR R2, R0, #15   ;; Last byte
    OR  R0, R1, R2

    ;; ... read in that block
    FS_RAW_READ

    ;; This is a full block
    LD R0, FS_BLOCK_SIZE

    ;; This are the destination and source of the first Directory Datablock
    LD R1, FS_FIRST_DIRECTORY_DATA_BLOCK
    LD R2, FS_BASE_ADDRESS

    FS_COPY_WORDS

    ;; Loop through the entries looking for our file
    ;; Reload the registers
    LD R0, FIND_INODE_SAVE_R0
    LD R1, FIND_INODE_SAVE_R1
    LD R2, FIND_INODE_SAVE_R2

    LD R4, FS_FIRST_DIRECTORY_DATA_BLOCK
FIND_INODE_DATA_BLOCK_LOOP
    ;; Get the record length
    LDR R5, R4, #4
    SHFli R5, R5, #4
    SHFLi R5, R5, #4
    LDR R3, R4, #5
    OR R5, R3, R5  ;; R5 Now contains the record length
    ;; Make sure this is not zero
    BRp FIND_INODE_COMPARE_ENTRY_NAME_LENGTH
    LC R3, #-1     ;; -1 is a failure code indicating that we couldn't find the INode!
    BR FIND_INODE_DONE

FIND_INODE_COMPARE_ENTRY_NAME_LENGTH
    ST R5, FIND_INODE_RECORDLEN
    ;; Get the name length
    LDR R5, R4, #6
    SHFli R5, R5, #4
    SHFLi R5, R5, #4
    LDR R3, R4, #7
    OR R5, R3, R5 ;; R5 now contains the name length

    ;; Compare this length with the length of our path component
    ADD R5, R5, #-1 ;; Subtract the space for the '\0'
    ADD R5, R5, R1  ;; Add the starting address
    SUB R5, R5, R2  ;; Subtract the ending address
    BRnp FIND_INODE_NAMES_DONT_MATCH

    ;; The names have the same length, so we need to compare them character by character
    ;;      Loop over the characters

FIND_INODE_NAMES_DONT_MATCH
    LD R5, FIND_INODE_RECORDLEN
    ADD R4, R4, R5
    BR FIND_INODE_DATA_BLOCK_LOOP



    ;; Get the INode number
FIND_INODE_DONE
    LD R0, FIND_INODE_SAVE_R0
    LD R1, FIND_INODE_SAVE_R1
    LD R2, FIND_INODE_SAVE_R2
    LD R4, FIND_INODE_SAVE_R4
    LD R5, FIND_INODE_SAVE_R5
    RTT



STR_SAVE_R1 .BLKW 1
STR_SAVE_R2 .BLKW 1
STR_SAVE_R3 .BLKW 1

TRAP_FIND_STRING_LENGTH
    ST R1, STR_SAVE_R1
    ST R2, STR_SAVE_R2
    ST R3, STR_SAVE_R3

    ;; R2 contains the number of characters
    CLR R2

FS_FIND_STRING_LENGTH_LOOP
    LDR R1, R0, #0
    CMPi R1, #0
    BRz FS_FIND_STRING_LENGTH_DONE
    ADD R2, R2, #1
    ADD R0, R0, #1
    BR FS_FIND_STRING_LENGTH_LOOP

FS_FIND_STRING_LENGTH_DONE
    CPR R0, R2
    LD R1, STR_SAVE_R1
    LD R2, STR_SAVE_R2
    LD R3, STR_SAVE_R3
    RTT
